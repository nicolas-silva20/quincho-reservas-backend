package com.elumbral.quincho.model.enums;

public enum EstadoPago {
    PENDIENTE,
    SENA_PAGADA,      // 50%
    PAGADO_COMPLETO,  // 100%
    DEPOSITO_RETENIDO,
    DEPOSITO_DEVUELTO
}