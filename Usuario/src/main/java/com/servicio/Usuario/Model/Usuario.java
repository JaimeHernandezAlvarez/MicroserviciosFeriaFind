package com.servicio.Usuario.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USUARIO") // Nombre de la tabla en la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

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
    private String foto; // Puede ser una URL o base64

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "HORARIO", length = 100)
    private String horario;

    @Column(name = "CONTRASENA", nullable = false, length = 255)
    private String contrasena; // Longitud amplia para almacenar hashes
}
