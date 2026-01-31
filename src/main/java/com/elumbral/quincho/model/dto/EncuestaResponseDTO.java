package com.elumbral.quincho.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncuestaResponseDTO {

    private Long id;
    private String nombreCliente;
    private String telefonoCliente;
    private Long reservaId;

    // Preguntas principales
    private Integer satisfaccionGeneral;
    private Integer cumplioExpectativas;
    private Integer recomendaria;
    private Integer volveria;

    // Feedback textual
    private String porQueRecomendaria;
    private String queGusto;
    private String queMejorar;
    private String queAgregar;

    private LocalDateTime fechaRespuesta;
}