package com.elumbral.quincho.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class FechaNoDisponibleException extends RuntimeException {

    public FechaNoDisponibleException(String mensaje) {
        super(mensaje);
    }

    public FechaNoDisponibleException(LocalDate fecha, LocalTime hora) {
        super(String.format("La fecha %s a las %s no está disponible", fecha, hora));
    }
}