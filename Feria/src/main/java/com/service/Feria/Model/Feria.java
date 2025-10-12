package com.service.Feria.Model;

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
@Table(name = "Feria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feria_seq")
    @SequenceGenerator(name = "feria_seq", sequenceName = "FERIAS_SEQ", allocationSize = 1)
    @Column(name = "id_feria")
    private Integer id;

    @Column(name = "nombre_feria")
    private String nombre;
}