package com.example.productapi;

import jakarta.persistence.*;

@Entity
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}
