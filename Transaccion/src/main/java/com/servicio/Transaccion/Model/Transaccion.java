package com.servicio.Transaccion.Model;

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
@Table(name = "Transaccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaccion_seq")
    @SequenceGenerator(name = "transaccion_seq", sequenceName = "TRANSACCIONES_SEQ", allocationSize = 1)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "fecha")
    private java.sql.Date fecha;

    @Column(name = "monto_total")
    private Double montoTotal;

    @Column(name = "estado")
    private String estado;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_vendedor")
    private Integer idVendedor;

    @Column(name = "id_pago")
    private Integer idPago;
}
