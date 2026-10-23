package com.example.productapi;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
    private String nome;
    private String categoria;
    private Integer quantita;
    private LocalDate dataScadenza;
    private LocalDate dataInserimento;
    private String note;
    private boolean notificaInviata;
    private boolean consumato = false;

    public Product() {
    }
    
    public Integer getId() {
    	return id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCategoria() {
    	return categoria;
    }
    
    public void setCategoria(String categoria) {
    	this.categoria = categoria;
    }
    
    public Integer getQuantita() {
    	return quantita;
    }
    
    public void setQuantita(Integer quantita) {
    	this.quantita = quantita;
    }
    
    public LocalDate getDataScadenza() {
    	return dataScadenza;
    }
    
    public void setDataScadenza(LocalDate dataScadenza) {
    	this.dataScadenza = dataScadenza;
    }
    
    public LocalDate getdataInserimento() {
    	return dataInserimento;
    }
    
    public void setDataInserimento (LocalDate dataInserimento ) {
    	this.dataInserimento = dataInserimento;
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public boolean isNotificaInviata() {
    	return notificaInviata;
    }
    
    public void setNotificaInviata(boolean notificaInviata) {
    	this.notificaInviata = notificaInviata;
    }
    
    public boolean isConsumato() {
    	return consumato;
    }
    
    public void setConsumato(boolean consumato) {
    	this.consumato = consumato;
    }
}
