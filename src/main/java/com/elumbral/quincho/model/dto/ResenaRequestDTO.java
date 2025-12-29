package com.elumbral.quincho.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResenaRequestDTO {
    private String token;
    private Integer calificacion;
    private String comentario;
}