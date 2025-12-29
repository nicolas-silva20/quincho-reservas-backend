package com.elumbral.quincho.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisponibilidadDTO {

    private LocalDate fecha;
    private LocalTime hora;
    private Boolean disponible;
    private String mensaje;
}