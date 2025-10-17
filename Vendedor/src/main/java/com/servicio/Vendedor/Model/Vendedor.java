package com.servicio.Vendedor.Model; // Reemplaza con el nombre de tu paquete

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob; // Importante para el campo 'text'
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VENDEDOR") // Nombre de la tabla en mayúsculas, como en tu plantilla
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendedor_seq")
    @SequenceGenerator(name = "vendedor_seq", sequenceName = "VENDEDOR_SEQ", allocationSize = 1)
    @Column(name = "ID_VENDEDOR")
    private Integer idVendedor;

    @Column(name = "NOMBRE_VENDEDOR", length = 100, nullable = false)
    private String nombreVendedor;

    @Lob // Anotación para campos 'text' (mapea a CLOB en Oracle)
    @Column(name = "DESCRIPCION") // Variable 'descripcion' sin tilde
    private String descripcion;

    @Column(name = "ESTADO", length = 50)
    private String estado;

    @Column(name = "FOTO_PERFIL", length = 255) // Longitud estándar para URLs o rutas de archivos
    private String fotoPerfil;

    @Column(name = "ID_USUARIO", nullable = false) // Clave foránea, asumimos que no puede ser nula
    private Integer idUsuario;
}