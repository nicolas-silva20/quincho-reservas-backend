package com.elumbral.quincho.service;

import com.elumbral.quincho.model.dto.DisponibilidadDTO;
import com.elumbral.quincho.model.entity.Reserva;
import com.elumbral.quincho.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisponibilidadService {

    private final ReservaRepository reservaRepository;
    
    // Horarios de turnos
    private static final LocalTime TURNO_TARDE = LocalTime.of(13, 0);
    private static final LocalTime TURNO_NOCHE = LocalTime.of(20, 0);

    /**
     * Verifica si una fecha y hora están disponibles
     * Implementa bloqueo cruzado: si hay reserva en un turno, bloquea el otro turno del mismo día
     */
    public DisponibilidadDTO verificarDisponibilidad(LocalDate fecha, LocalTime hora) {
        // Validar que la fecha no sea en el pasado
        if (fecha.isBefore(LocalDate.now())) {
            return DisponibilidadDTO.builder()
                    .fecha(fecha)
                    .hora(hora)
                    .disponible(false)
                    .mensaje("No se pueden hacer reservas para fechas pasadas")
                    .build();
        }

        // Obtener todas las reservas activas del día
        List<Reserva> reservasDelDia = reservaRepository.findReservasActivasByFecha(fecha);

        // Si hay cualquier reserva activa ese día, el día completo está bloqueado
        if (!reservasDelDia.isEmpty()) {
            Reserva reservaExistente = reservasDelDia.get(0);
            String turnoOcupado = esTurnoTarde(reservaExistente.getHoraInicio()) ? "Tarde (13:00-19:00)" : "Noche (20:00-01:30)";
            
            return DisponibilidadDTO.builder()
                    .fecha(fecha)
                    .hora(hora)
                    .disponible(false)
                    .mensaje("Fecha no disponible. Turno " + turnoOcupado + " ya reservado. Solo se permite un turno por día.")
                    .build();
        }

        return DisponibilidadDTO.builder()
                .fecha(fecha)
                .hora(hora)
                .disponible(true)
                .mensaje("Fecha y turno disponibles")
                .build();
    }

    /**
     * Determina si una hora corresponde al turno de la tarde
     */
    private boolean esTurnoTarde(LocalTime hora) {
        return hora.equals(TURNO_TARDE) || hora.isBefore(TURNO_NOCHE);
    }

    /**
     * Obtiene todas las reservas activas de una fecha específica
     */
    public List<Reserva> obtenerReservasActivasPorFecha(LocalDate fecha) {
        return reservaRepository.findReservasActivasByFecha(fecha);
    }

    /**
     * Verifica si hay espacio disponible (máximo 1 turno por día)
     */
    public boolean estaDisponible(LocalDate fecha, LocalTime hora) {
        List<Reserva> reservasDelDia = reservaRepository.findReservasActivasByFecha(fecha);
        return reservasDelDia.isEmpty();
    }
}