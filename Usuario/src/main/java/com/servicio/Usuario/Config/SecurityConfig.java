package com.servicio.Usuario.Config; // Asegúrate de que este sea el paquete de tu configuración

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

/**
 * Configuración central de Spring Security.
 * Define qué rutas son públicas y cuáles están protegidas.
 */
@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring
public class SecurityConfig {

    /**
     * Define el bean para el codificador de contraseñas.
     * Usamos BCrypt, que es el estándar actual para hashear contraseñas de forma segura.
     * @return una instancia de PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * Aquí se definen las reglas de autorización para los endpoints de la aplicación.
     * @param http el objeto HttpSecurity para configurar.
     * @return la cadena de filtros de seguridad construida.
     * @throws Exception si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilitar CSRF (Cross-Site Request Forgery)
            // Esencial para que las APIs REST sin estado funcionen correctamente con POST, PUT, DELETE.
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. Definir las reglas de autorización para las peticiones HTTP
            .authorizeHttpRequests(auth -> auth
                
                // 2a. Permitir acceso PÚBLICO y sin autenticación a la documentación de Swagger
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()
                
                // 2b. Permitir peticiones POST PÚBLICAS para el endpoint de registro de usuarios
                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                
                // 2c. Para CUALQUIER OTRA petición, se requiere que el usuario esté autenticado
                .anyRequest().authenticated()
            )
            
            // 3. Habilitar la autenticación básica (HTTP Basic Auth)
            // Esto es ideal para APIs, ya que no redirige a un formulario de login.
            .httpBasic(withDefaults());

        return http.build();
    }
}