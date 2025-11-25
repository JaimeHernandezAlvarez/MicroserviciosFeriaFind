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
            .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF para APIs
            .authorizeHttpRequests(auth -> auth
                // ------------------------------------------------------------
                // 1. ZONA PÚBLICA: SWAGGER (Documentación)
                // ------------------------------------------------------------
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/webjars/**",
                    "/configuration/ui",
                    "/configuration/security"
                ).permitAll()

                // ------------------------------------------------------------
                // 2. ZONA PÚBLICA: AUTH (Registro y Login)
                // ------------------------------------------------------------
                // Permitimos POST a /usuarios (Registro)
                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                
                // Permitimos POST a /usuarios/login (Login) -> ¡ESTA ES LA CLAVE!
                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios/login").permitAll()
                
                // Permitimos GET a /usuarios/buscar (Para el update del perfil)
                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/buscar").permitAll()

                // ------------------------------------------------------------
                // 3. ZONA PRIVADA: Todo lo demás requiere contraseña
                // ------------------------------------------------------------
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());

        return http.build();
    }
}