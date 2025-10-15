package com.example.Ubicacion.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal; // Es importante importar BigDecimal para los campos decimales
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UBICACION") // Generalmente, los nombres de tabla van en mayúsculas por convención
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ubicacion_seq")
    @SequenceGenerator(name = "ubicacion_seq", sequenceName = "UBICACION_SEQ", allocationSize = 1)
    @Column(name = "ID_UBICACION")
    private Integer idUbicacion;

    @Column(name = "NOMBRE_FERIA", length = 100, nullable = false) // Es buena práctica definir longitud y si puede ser nulo
    private String nombreFeria;
    
    @Column(name = "DIRECCION", length = 255)
    private String direccion;
    
    @Column(name = "LATITUD", precision = 10, scale = 8) // 'precision' es el total de dígitos, 'scale' es el número de decimales
    private BigDecimal latitud;
    
    @Column(name = "LONGITUD", precision = 11, scale = 8)
    private BigDecimal longitud;
}