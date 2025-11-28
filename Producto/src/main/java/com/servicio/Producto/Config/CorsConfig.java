package com.servicio.Producto.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todos los endpoints de la API
                .allowedOrigins("http://localhost:5173") // URLs permitidas
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Verbos permitidos
                .allowedHeaders("*") // Permitir todos los headers
                .allowCredentials(true); // Si usas cookies o autenticación
    }
}