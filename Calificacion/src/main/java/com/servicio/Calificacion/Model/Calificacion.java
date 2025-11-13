package com.servicio.Calificacion.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "CALIFICACION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calificacion_seq")
    @SequenceGenerator(
            name = "calificacion_seq",
            sequenceName = "CALIFICACION_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID_CALIFICACION")
    private Integer idCalificacion;

    @Column(name = "VALOR", nullable = false)
    private Integer valor;

    // ✅ En Oracle no existe TEXT → se reemplaza por CLOB
    @Lob
    @Column(name = "COMENTARIO")
    private String comentario;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @Column(name = "ID_USUARIO", nullable = false)
    private Integer idUsuario;

    @Column(name = "ID_VENDEDOR", nullable = false)
    private Integer idVendedor;
}
