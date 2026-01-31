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
public class ResenaDTO {

    private Long id;
    private String nombreCliente;
    private String comentario;
    private Integer calificacion;
    private LocalDateTime fechaCreacion;
    private Boolean aprobada;
    
    // Campos opcionales para generación de token de encuesta
    private String encuestaToken;
    private String telefonoCliente;
    private Long reservaId;
}