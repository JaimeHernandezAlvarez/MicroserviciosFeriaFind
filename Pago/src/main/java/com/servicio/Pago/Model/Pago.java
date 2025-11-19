package com.servicio.Pago.Model;

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

import java.util.Date;

@Entity
@Table(name = "PAGO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pago_seq")
    @SequenceGenerator(name = "pago_seq", sequenceName = "PAGO_SEQ", allocationSize = 1)
    @Column(name = "id_pago")
    private Integer idPago;

    @Column(name = "metodo", nullable = false, length = 50)
    private String metodo;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Column(name = "fecha_pago")
    private Date fechaPago;

    @Column(name = "referencia_pasarela", length = 100)
    private String referenciaPasarela;
}
