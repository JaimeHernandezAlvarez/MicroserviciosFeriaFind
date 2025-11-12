package com.servicio.DetalleUsuario.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DETALLE_USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_usuario_seq")
    @SequenceGenerator(name = "detalle_usuario_seq", sequenceName = "DETALLE_USUARIO_SEQ", allocationSize = 1)
    @Column(name = "ID_DETALLE")
    private Integer idDetalle;

    @Column(name = "ID_USUARIO", nullable = false)
    private Integer idUsuario;

    @Column(name = "ID_VENDEDOR", nullable = false)
    private Integer idVendedor;
}
