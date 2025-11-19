package com.servicio.DetalleTransaccion.Model;

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
@Table(name = "DetalleTransaccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_trans_seq")
    @SequenceGenerator(name = "detalle_trans_seq", sequenceName = "DETALLE_TRANS_SEQ", allocationSize = 1)
    @Column(name = "id_detalle_trans")
    private Integer id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "id_transaccion")
    private Integer idTransaccion;
}
