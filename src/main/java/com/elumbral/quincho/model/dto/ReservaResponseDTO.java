package com.elumbral.quincho.model.dto;

import com.elumbral.quincho.model.enums.EstadoPago;
import com.elumbral.quincho.model.enums.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponseDTO {

    private Long id;
    private String nombreCliente;
    private String telefonoCliente;
    private String emailCliente;
    private String nombreExperiencia;
    private LocalDate fechaEvento;
    private LocalTime horaInicio;
    private BigDecimal precioExperiencia;
    private BigDecimal depositoGarantia;
    private BigDecimal precioTotal;
    private EstadoReserva estado;
    private EstadoPago estadoPago;
    private LocalDateTime fechaCreacion;
    private String horarioContacto;
    private String observaciones;

    // Nuevo campo para sistema de reseñas
    private String resenaToken;
    private Boolean tokenUsado;
}