package com.elumbral.quincho.repository;

import com.elumbral.quincho.model.entity.Reserva;
import com.elumbral.quincho.model.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByClienteId(Long clienteId);

    List<Reserva> findByEstado(EstadoReserva estado);

    List<Reserva> findByFechaEvento(LocalDate fechaEvento);

    @Query("SELECT r FROM Reserva r WHERE r.fechaEvento = :fecha " +
            "AND r.estado NOT IN ('CANCELADA_CLIENTE', 'CANCELADA_ADMIN')")
    List<Reserva> findReservasActivasByFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT r FROM Reserva r WHERE r.fechaEvento = :fecha " +
            "AND r.horaInicio = :hora " +
            "AND r.estado NOT IN ('CANCELADA_CLIENTE', 'CANCELADA_ADMIN')")
    List<Reserva> findReservasActivasByFechaAndHora(
            @Param("fecha") LocalDate fecha,
            @Param("hora") LocalTime hora
    );

    @Query("SELECT r FROM Reserva r WHERE r.fechaEvento BETWEEN :inicio AND :fin " +
            "ORDER BY r.fechaEvento ASC")
    List<Reserva> findReservasByRangoFechas(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.estado = :estado")
    Long countByEstado(@Param("estado") EstadoReserva estado);

    // ========================================
    // NUEVO: BÚSQUEDA POR TOKEN DE RESEÑA
    // ========================================

    Optional<Reserva> findByResenaToken(String token);

    // ========================================
    // NUEVO: BÚSQUEDA POR TOKEN DE ENCUESTA
    // ========================================

    Optional<Reserva> findByEncuestaToken(String token);
}