package com.servicio.Usuario.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USUARIO")
@Data // Lombok genera getters, setters, toString, etc.
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails { // <--- 1. Implementamos la interfaz

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "NOMBRE_USUARIO", nullable = false, length = 50)
    private String nombreUsuario;

    @Column(name = "CORREO_ELECTRONICO", nullable = false, unique = true, length = 100)
    private String correoElectronico;

    @Column(name = "FOTO", length = 500)
    private String foto;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "HORARIO", length = 100)
    private String horario;

    // En Usuario.java
    @Column(name = "CONTRASENA", nullable = false, length = 255)
    private String contrasena;

    // 1. NUEVO CAMPO
    @Enumerated(EnumType.STRING) // Guarda el texto "ADMIN" o "USER" en la BD
    @Column(name = "ROL")
    private Role rol; 

    // 2. ACTUALIZAMOS EL MÉTODO DE AUTORIDADES
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security espera que los roles empiecen con "ROLE_"
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    // =================================================================
    // MÉTODOS DE USER DETAILS (Spring Security)
    // =================================================================
    @Override
    public String getPassword() {
        // 3. Conectamos lo que Spring llama "password" con tu campo "contrasena"
        return contrasena;
    }

    @Override
    public String getUsername() {
        // 4. Conectamos lo que Spring llama "username" con tu campo "correoElectronico"
        // Esto es CRUCIAL para que el login funcione con email
        return correoElectronico;
    }

    // 5. Gestión de estado de cuenta (Devolvemos true para habilitar todo por defecto)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}