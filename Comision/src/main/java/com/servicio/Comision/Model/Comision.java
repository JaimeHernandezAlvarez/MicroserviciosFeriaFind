package com.servicio.Comision.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "COMISION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comision {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comision_seq")
    @SequenceGenerator(name = "comision_seq", sequenceName = "COMISION_SEQ", allocationSize = 1)
    @Column(name = "id_comision")
    private Integer idComision;

    @Column(name = "porcentaje", nullable = false)
    private BigDecimal porcentaje;

    @Column(name = "monto_aplicado", nullable = false)
    private BigDecimal montoAplicado;

    @Column(name = "id_transaccion", nullable = false)
    private Integer idTransaccion;
}
