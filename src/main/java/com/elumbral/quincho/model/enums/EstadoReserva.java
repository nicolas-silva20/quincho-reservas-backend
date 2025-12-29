package com.elumbral.quincho.model.enums;

public enum EstadoReserva {
    PENDIENTE,           // Reserva creada, esperando confirmación
    PRE_CONFIRMADA,      // Cliente confirmó, esperando visita
    CONFIRMADA,          // Visitó y pagó seña
    PAGADA_COMPLETA,     // Pagó el 100%
    EN_CURSO,            // Evento en progreso
    FINALIZADA,          // Evento terminado, esperando devolución depósito
    CANCELADA_CLIENTE,   // Cancelada por el cliente
    CANCELADA_ADMIN,     // Cancelada por administrador
    DEPOSITO_DEVUELTO    // Depósito devuelto, proceso completo
}