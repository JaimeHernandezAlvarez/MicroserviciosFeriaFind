package com.servicio.DetalleProducto.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DETALLE_PRODUCTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_producto_seq")
    @SequenceGenerator(name = "detalle_producto_seq", sequenceName = "DETALLE_PRODUCTO_SEQ", allocationSize = 1)
    @Column(name = "ID_DETALLE")
    private Integer idDetalle;

    @Column(name = "ID_PRODUCTO", nullable = false)
    private Integer idProducto;

    @Column(name = "ID_CATEGORIA", nullable = false)
    private Integer idCategoria;
}
