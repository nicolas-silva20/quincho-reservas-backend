package com.elumbral.quincho.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncuestaRequestDTO {

    @NotBlank(message = "El token es obligatorio")
    private String token;

    // Preguntas principales (obligatorias)
    @NotNull(message = "La satisfacción general es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer satisfaccionGeneral;

    @NotNull(message = "Cumplió expectativas es obligatorio")
    @Min(1)
    @Max(5)
    private Integer cumplioExpectativas;

    @NotNull(message = "Recomendaría es obligatorio")
    @Min(1)
    @Max(5)
    private Integer recomendaria;

    @NotNull(message = "Volvería es obligatorio")
    @Min(1)
    @Max(5)
    private Integer volveria;

    // Feedback textual (opcional)
    private String porQueRecomendaria;
    private String queGusto;
    private String queMejorar;
    private String queAgregar;
}