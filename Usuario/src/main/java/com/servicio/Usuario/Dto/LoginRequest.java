package com.servicio.Usuario.Dto;

import com.fasterxml.jackson.annotation.JsonProperty; // Importante importar esto

public class LoginRequest {
    
    // Con esto, Java buscará "correoElectronico" en el JSON y lo guardará aquí
    @JsonProperty("correoElectronico") 
    private String email;
    
    private String password;
    
    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}