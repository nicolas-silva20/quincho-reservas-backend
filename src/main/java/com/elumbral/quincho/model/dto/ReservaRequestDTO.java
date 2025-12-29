package com.elumbral.quincho.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    private String email;

    @NotNull(message = "El ID de la experiencia es obligatorio")
    private Long experienciaId;

    @NotNull(message = "La fecha del evento es obligatoria")
    private LocalDate fechaEvento;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotBlank(message = "El horario de contacto es obligatorio")
    private String horarioContacto;

    @NotNull(message = "Debe aceptar los términos y condiciones")
    private Boolean terminosAceptados;

    private String observaciones;
    
    // Campos para gestión de precios dinámicos
    private Double precioBase;
    private Double precioExtras;
    private Double precioTotal;
}