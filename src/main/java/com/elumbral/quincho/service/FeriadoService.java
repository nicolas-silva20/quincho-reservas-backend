package com.elumbral.quincho.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Servicio para gestionar feriados argentinos
 * Los feriados tienen precio de fin de semana independientemente del día de la semana
 */
@Service
@Slf4j
public class FeriadoService {

    // Feriados argentinos 2026
    private static final Set<LocalDate> FERIADOS_2026 = new HashSet<>();

    static {
        // Enero
        FERIADOS_2026.add(LocalDate.of(2026, 1, 1));  // Año Nuevo

        // Febrero
        FERIADOS_2026.add(LocalDate.of(2026, 2, 16)); // Carnaval - Lunes
        FERIADOS_2026.add(LocalDate.of(2026, 2, 17)); // Carnaval - Martes

        // Marzo
        FERIADOS_2026.add(LocalDate.of(2026, 3, 23)); // Día no laborable con fines turísticos
        FERIADOS_2026.add(LocalDate.of(2026, 3, 24)); // Día de la Memoria

        // Abril
        FERIADOS_2026.add(LocalDate.of(2026, 4, 2));  // Día del Veterano y Caídos en Malvinas
        FERIADOS_2026.add(LocalDate.of(2026, 4, 3));  // Viernes Santo

        // Mayo
        FERIADOS_2026.add(LocalDate.of(2026, 5, 1));  // Día del Trabajo
        FERIADOS_2026.add(LocalDate.of(2026, 5, 25)); // Revolución de Mayo

        // Junio
        FERIADOS_2026.add(LocalDate.of(2026, 6, 15)); // Güemes (trasladado)
        FERIADOS_2026.add(LocalDate.of(2026, 6, 20)); // Belgrano

        // Julio
        FERIADOS_2026.add(LocalDate.of(2026, 7, 9));  // Independencia
        FERIADOS_2026.add(LocalDate.of(2026, 7, 10)); // Día no laborable turístico

        // Agosto
        FERIADOS_2026.add(LocalDate.of(2026, 8, 17)); // San Martín (trasladado)

        // Septiembre
        FERIADOS_2026.add(LocalDate.of(2026, 9, 12)); // Año Nuevo Judío
        FERIADOS_2026.add(LocalDate.of(2026, 9, 13)); // Año Nuevo Judío
        FERIADOS_2026.add(LocalDate.of(2026, 9, 21)); // Día del Perdón

        // Octubre
        FERIADOS_2026.add(LocalDate.of(2026, 10, 12)); // Día de la Raza

        // Noviembre
        FERIADOS_2026.add(LocalDate.of(2026, 11, 23)); // Soberanía Nacional (trasladado)

        // Diciembre
        FERIADOS_2026.add(LocalDate.of(2026, 12, 7));  // Día no laborable turístico
        FERIADOS_2026.add(LocalDate.of(2026, 12, 8));  // Inmaculada Concepción
        FERIADOS_2026.add(LocalDate.of(2026, 12, 25)); // Navidad
    }

    /**
     * Verifica si una fecha es feriado
     * @param fecha La fecha a verificar
     * @return true si es feriado, false en caso contrario
     */
    public boolean esFeriado(LocalDate fecha) {
        boolean esFeriado = FERIADOS_2026.contains(fecha);
        if (esFeriado) {
            log.debug("Fecha {} es feriado - se aplicará precio de fin de semana", fecha);
        }
        return esFeriado;
    }

    /**
     * Verifica si una fecha es sábado, domingo o feriado
     * @param fecha La fecha a verificar
     * @return true si es fin de semana o feriado
     */
    public boolean esFinDeSemanaOFeriado(LocalDate fecha) {
        if (fecha == null) {
            return false;
        }
        
        // Verificar fin de semana (sábado=6, domingo=7)
        int diaSemana = fecha.getDayOfWeek().getValue();
        boolean esFinDeSemana = (diaSemana == 6 || diaSemana == 7);
        
        // Verificar feriado
        boolean esFeriado = esFeriado(fecha);
        
        return esFinDeSemana || esFeriado;
    }
}
