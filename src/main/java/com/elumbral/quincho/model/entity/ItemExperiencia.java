package com.elumbral.quincho.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;

@Entity
@Table(name = "items_experiencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemExperiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "experiencia_id", nullable = false)
    @JsonBackReference
    private Experiencia experiencia;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Boolean incluido;

    @Column(precision = 10, scale = 2)
    private BigDecimal costoAdicional;

    private Boolean obligatorio = false;
}