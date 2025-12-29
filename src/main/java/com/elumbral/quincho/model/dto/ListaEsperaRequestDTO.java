package com.elumbral.quincho.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListaEsperaRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    private String email;

    @NotNull(message = "La experiencia es obligatoria")
    private Long experienciaId;

    @NotNull(message = "La fecha deseada es obligatoria")
    private LocalDate fechaDeseada;

    @NotNull(message = "La hora deseada es obligatoria")
    private LocalTime horaDeseada;
}