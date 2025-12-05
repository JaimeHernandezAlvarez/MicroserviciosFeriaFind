package com.servicio.Usuario.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth

                // =========================================
                // SWAGGER Y DOCUMENTACIÓN — SIEMPRE PUBLICO
                // =========================================
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/doc/**"
                ).permitAll()

                // ================================
                // ENDPOINTS DE AUTENTICACIÓN PÚBLICOS
                // ================================
                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()       // Registro
                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios/login").permitAll() // Login

                // ==========================================
                // RUTAS RESTRINGIDAS A ADMIN
                // ==========================================
                .requestMatchers("/api/v1/usuarios/admin/**").hasRole("ADMIN")

                // ==========================================
                // RUTAS AUTENTICADAS
                // (cualquier usuario con login: USER o ADMIN)
                // ==========================================
                .requestMatchers("/api/v1/usuarios/**").authenticated()

                // ==========================================
                // TODO LO DEMÁS TAMBIÉN REQUIERE AUTENTICACIÓN
                // ==========================================
                .anyRequest().authenticated()
            )

            // ==========================================
            // Stateless → JWT sin sesiones
            // ==========================================
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Provider (password + userDetails)
            .authenticationProvider(authenticationProvider)

            // Filtro JWT antes del filtro estándar
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
            "http://localhost:5173", 
            "https://tu-frontend-en-render.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
