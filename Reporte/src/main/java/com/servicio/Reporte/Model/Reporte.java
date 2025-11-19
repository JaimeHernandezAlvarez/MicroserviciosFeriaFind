package com.servicio.Reporte.Model;

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

import java.time.LocalDate;

@Entity
@Table(name = "REPORTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reporte_seq")
    @SequenceGenerator(name = "reporte_seq", sequenceName = "REPORTE_SEQ", allocationSize = 1)
    @Column(name = "id_reporte")
    private Integer id;

    @Column(name = "tipo_reporte")
    private String tipoReporte;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_vendedor")
    private Integer idVendedor;
}
