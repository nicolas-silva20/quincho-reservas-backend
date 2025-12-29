package com.elumbral.quincho.service;

import com.elumbral.quincho.model.dto.ResenaDTO;
import com.elumbral.quincho.model.dto.ResenaRequestDTO;
import com.elumbral.quincho.model.entity.Resena;
import com.elumbral.quincho.model.entity.Reserva;
import com.elumbral.quincho.model.enums.EstadoReserva;
import com.elumbral.quincho.repository.ResenaRepository;
import com.elumbral.quincho.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final ReservaRepository reservaRepository;

    /**
     * Obtener reseñas destacadas (últimas 3 aprobadas)
     */
    public List<ResenaDTO> obtenerResenasDestacadas() {
        return resenaRepository.findTop3ByAprobadaTrueOrderByFechaCreacionDesc()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener todas las reseñas aprobadas (público)
     */
    public List<ResenaDTO> obtenerResenasAprobadas() {
        return resenaRepository.findByAprobadaTrueOrderByFechaCreacionDesc()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Crear reseña usando token de reserva completada
     */
    @Transactional
    public ResenaDTO crearResenaConToken(ResenaRequestDTO request) {
        log.info("Intentando crear reseña con token");

        // Validar calificación
        if (request.getCalificacion() < 1 || request.getCalificacion() > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        // Buscar reserva por token
        Reserva reserva = reservaRepository.findByResenaToken(request.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token inválido o expirado"));

        // Validar que la reserva esté finalizada
        if (reserva.getEstado() != EstadoReserva.FINALIZADA) {
            throw new IllegalArgumentException("Solo se pueden dejar reseñas para reservas finalizadas");
        }

        // Validar que el token no haya sido usado
        if (reserva.getTokenUsado()) {
            throw new IllegalArgumentException("Este token ya fue utilizado para dejar una reseña");
        }

        // Crear la reseña
        Resena resena = new Resena();
        resena.setReserva(reserva);
        resena.setCliente(reserva.getCliente());
        resena.setCalificacion(request.getCalificacion());
        resena.setComentario(request.getComentario());
        resena.setFechaCreacion(LocalDateTime.now());
        resena.setAprobada(false); // Por defecto pendiente de aprobación

        resena = resenaRepository.save(resena);

        // Marcar token como usado
        reserva.setTokenUsado(true);
        reservaRepository.save(reserva);

        log.info("Reseña creada exitosamente con ID: {}", resena.getId());
        return convertirADTO(resena);
    }

    /**
     * Obtener todas las reseñas (admin)
     */
    public List<ResenaDTO> obtenerTodasLasResenas() {
        return resenaRepository.findAllByOrderByFechaCreacionDesc()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Aprobar una reseña
     */
    @Transactional
    public ResenaDTO aprobarResena(Long id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));

        resena.setAprobada(true);
        resena.setFechaAprobacion(LocalDateTime.now());

        log.info("Reseña {} aprobada", id);
        return convertirADTO(resenaRepository.save(resena));
    }

    /**
     * Rechazar/ocultar una reseña
     */
    @Transactional
    public ResenaDTO rechazarResena(Long id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));

        resena.setAprobada(false);
        resena.setFechaAprobacion(null);

        log.info("Reseña {} rechazada/ocultada", id);
        return convertirADTO(resenaRepository.save(resena));
    }

    /**
     * Eliminar reseña permanentemente
     */
    @Transactional
    public void eliminarResena(Long id) {
        if (!resenaRepository.existsById(id)) {
            throw new IllegalArgumentException("Reseña no encontrada");
        }

        resenaRepository.deleteById(id);
        log.info("Reseña {} eliminada permanentemente", id);
    }

    /**
     * Convertir entidad a DTO
     */
    private ResenaDTO convertirADTO(Resena resena) {
        return ResenaDTO.builder()
                .id(resena.getId())
                .nombreCliente(resena.getCliente() != null ? resena.getCliente().getNombre() : "Cliente Desconocido")
                .comentario(resena.getComentario())
                .calificacion(resena.getCalificacion())
                .fechaCreacion(resena.getFechaCreacion())
                .aprobada(resena.getAprobada())
                .build();
    }
}