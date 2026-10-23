package com.example.productapi;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

    
    
    //Get ALL
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    
    //GET BY ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
    	return productService.getProductById(id);
    }
    
    //DELETE
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
    	productService.deleteProduct(id);
    }
    
    //GET BY CATEGORIA
    @GetMapping("/category/{categoria}")
    public List<Product> getProductsByCategoria(@PathVariable String categoria) {
    	return productService.getProductsByCategoria(categoria);
    }
    
    //RICERC PRODOTTO
    @GetMapping("/search/{nome}")
    public List<Product> search(@PathVariable String nome) {
    	return productService.searchByNome(nome);
    }
    
    @PostMapping
    public Product createProducts(@RequestBody Product product) {
        product.setDataInserimento(LocalDate.now());
        return productService.saveProduct(product);
    }
    
    @PutMapping("/{id}/decrease")
    public Product decreaseQuantity(@PathVariable int id) {
        return productService.decreaseQuantity(id);
    }
    
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
    
    @GetMapping("/stats")
    public Map<String, Long> getStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("totali", productService.countAll());
        stats.put("scaduti", productService.countExpired());
        stats.put("consumati", productService.countConsumed());

        return stats;
    }
   
    @PutMapping("/{id}/consume")
    public Product consume(@PathVariable int id) {
        return productService.markAsConsumed(id);
    }

  


}

 