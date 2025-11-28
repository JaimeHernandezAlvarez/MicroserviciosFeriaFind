package com.servicio.Usuario.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. AQUÍ ACTIVAMOS LA CONFIGURACIÓN DE CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF para APIs
            .authorizeHttpRequests(auth -> auth
                // ... (Tus configuraciones existentes siguen igual) ...
                
                // 1. ZONA PÚBLICA: SWAGGER
                .requestMatchers(
                    "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                    "/swagger-resources/**", "/webjars/**",
                    "/configuration/ui", "/configuration/security"
                ).permitAll()

                // 2. ZONA PÚBLICA: AUTH
                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/buscar").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/buscar/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/**").permitAll()

                // 3. ZONA PRIVADA
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());

        return http.build();
    }

    // 2. DEFINIMOS EL BEAN CON LAS REGLAS DE CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // A. ORÍGENES PERMITIDOS (¡Cambia esto por la URL de tu frontend!)
        // Ejemplo: React suele ser localhost:3000, Angular localhost:4200
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        
        // B. MÉTODOS PERMITIDOS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // C. HEADERS PERMITIDOS
        configuration.setAllowedHeaders(List.of("*"));
        
        // D. PERMITIR CREDENTIALS (Cookies / Auth Headers)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}