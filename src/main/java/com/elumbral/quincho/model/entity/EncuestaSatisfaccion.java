package com.elumbral.quincho.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "encuestas_satisfaccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncuestaSatisfaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    // ========================================
    // PREGUNTAS PRINCIPALES (Escala 1-5)
    // ========================================

    @Min(1)
    @Max(5)
    @Column(name = "satisfaccion_general", nullable = false)
    private Integer satisfaccionGeneral;

    @Min(1)
    @Max(5)
    @Column(name = "cumplio_expectativas", nullable = false)
    private Integer cumplioExpectativas;

    @Min(1)
    @Max(5)
    @Column(name = "recomendaria", nullable = false)
    private Integer recomendaria; // NPS Score

    @Min(1)
    @Max(5)
    @Column(name = "volveria", nullable = false)
    private Integer volveria;

    // ========================================
    // FEEDBACK TEXTUAL (Opcional)
    // ========================================

    @Column(name = "por_que_recomendaria", columnDefinition = "TEXT")
    private String porQueRecomendaria;

    @Column(name = "que_gusto", columnDefinition = "TEXT")
    private String queGusto;

    @Column(name = "que_mejorar", columnDefinition = "TEXT")
    private String queMejorar;

    @Column(name = "que_agregar", columnDefinition = "TEXT")
    private String queAgregar;

    // ========================================
    // METADATA
    // ========================================

    @Column(name = "fecha_respuesta", nullable = false)
    private LocalDateTime fechaRespuesta;

    @PrePersist
    protected void onCreate() {
        fechaRespuesta = LocalDateTime.now();
    }
}
