package com.example.productapi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findByCategoria(String categoria); 
	
	List<Product> findByNomeContainingIgnoreCase(String nome);
	
	long countByDataScadenzaBefore(java.time.LocalDate date);

	long countByQuantita(int quantita);
	
	long countByConsumatoTrue();

}