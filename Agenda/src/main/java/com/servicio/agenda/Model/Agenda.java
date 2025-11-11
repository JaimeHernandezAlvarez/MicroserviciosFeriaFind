package com.servicio.agenda.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "AGENDA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agenda_seq")
    @SequenceGenerator(name = "agenda_seq", sequenceName = "AGENDA_SEQ", allocationSize = 1)
    @Column(name = "ID_AGENDA")
    private Integer idAgenda;

    @Column(name = "DIA_SEMANA", nullable = false, length = 20)
    private String diaSemana;

    @Column(name = "HORA_INICIO", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "HORA_TERMINO", nullable = false)
    private LocalTime horaTermino;

    @Column(name = "ID_VENDEDOR", nullable = false)
    private Integer idVendedor;

    @Column(name = "ID_UBICACION", nullable = false)
    private Integer idUbicacion;
}
