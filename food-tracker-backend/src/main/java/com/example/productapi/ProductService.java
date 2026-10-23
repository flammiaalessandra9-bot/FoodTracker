package com.example.productapi;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProductService {
	
	//repository
	@Autowired
	private ProductRepository productRepository;
	
	//ottenere tutti i prodotti
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	//creare prodotto 
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
	
	//trovare prodotto per id
	public Product getProductById(int id) {
		return productRepository.findById(id).orElse(null);
	}
	
	//eliminare prodotto
	public void deleteProduct(int id) {
		productRepository.deleteById(id);;
	}
	
	//BY CATEGORIA
	public List<Product> getProductsByCategoria(String categoria) {
		return productRepository.findByCategoria(categoria);
	}
	
	//RICERCA PRODOTTI
	public List<Product> searchByNome(String nome) {
		return productRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	public long countAll() {
		return productRepository.count();
	}
	
	public Product decreaseQuantity(int id) {
	    Product p = productRepository.findById(id).orElse(null);

	    if (p != null && p.getQuantita() > 0) {
	        p.setQuantita(p.getQuantita() - 1);
	        return productRepository.save(p);
	    }

	    return p;
	}
	
	public Product updateProduct(int id, Product updated) {

	    Product p = productRepository.findById(id).orElse(null);

	    if (p != null) {
	        p.setNome(updated.getNome());
	        p.setCategoria(updated.getCategoria());
	        p.setQuantita(updated.getQuantita());
	        p.setDataScadenza(updated.getDataScadenza());
	        p.setNote(updated.getNote());

	        return productRepository.save(p);
	    }

	    return null;
	}
	
	public long countExpired() {
	    return productRepository.countByDataScadenzaBefore(java.time.LocalDate.now());
	}

	public long countConsumed() {
	    return productRepository.countByConsumatoTrue();
	}
	
	public Product markAsConsumed(int id) {
		Product p = productRepository.findById(id).orElse(null);
		if (p != null) {
			p.setConsumato(true);
			return productRepository.save(p);
		}
		return null;
	}
	
	}



