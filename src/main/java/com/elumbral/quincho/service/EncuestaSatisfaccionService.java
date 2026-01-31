package com.elumbral.quincho.service;

import com.elumbral.quincho.model.dto.EncuestaRequestDTO;
import com.elumbral.quincho.model.dto.EncuestaResponseDTO;
import com.elumbral.quincho.model.entity.EncuestaSatisfaccion;
import com.elumbral.quincho.model.entity.Reserva;
import com.elumbral.quincho.repository.EncuestaSatisfaccionRepository;
import com.elumbral.quincho.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EncuestaSatisfaccionService {

    private final EncuestaSatisfaccionRepository encuestaRepository;
    private final ReservaRepository reservaRepository;

    /**
     * Crear encuesta usando token de reserva con reseña procesada
     */
    @Transactional
    public EncuestaResponseDTO crearEncuestaConToken(EncuestaRequestDTO request) {
        log.info("Intentando crear encuesta con token");

        // Buscar reserva por token de encuesta
        Reserva reserva = reservaRepository.findByEncuestaToken(request.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token inválido o expirado"));

        // Validar que el token no haya sido usado
        if (reserva.getTokenEncuestaUsado()) {
            throw new IllegalArgumentException("Este token ya fue utilizado para completar la encuesta");
        }

        // Validar que no exista ya una encuesta para esta reserva
        if (encuestaRepository.existsByReserva(reserva)) {
            throw new IllegalArgumentException("Ya existe una encuesta para esta reserva");
        }

        // Crear la encuesta
        EncuestaSatisfaccion encuesta = new EncuestaSatisfaccion();
        encuesta.setReserva(reserva);
        encuesta.setCliente(reserva.getCliente());

        // Preguntas principales
        encuesta.setSatisfaccionGeneral(request.getSatisfaccionGeneral());
        encuesta.setCumplioExpectativas(request.getCumplioExpectativas());
        encuesta.setRecomendaria(request.getRecomendaria());
        encuesta.setVolveria(request.getVolveria());

        // Feedback textual (opcional)
        encuesta.setPorQueRecomendaria(request.getPorQueRecomendaria());
        encuesta.setQueGusto(request.getQueGusto());
        encuesta.setQueMejorar(request.getQueMejorar());
        encuesta.setQueAgregar(request.getQueAgregar());

        encuesta.setFechaRespuesta(LocalDateTime.now());

        // Guardar encuesta
        encuesta = encuestaRepository.save(encuesta);

        // Marcar token como usado
        reserva.setTokenEncuestaUsado(true);
        reservaRepository.save(reserva);

        log.info("Encuesta creada exitosamente con ID: {}", encuesta.getId());

        return convertirADTO(encuesta);
    }

    /**
     * Obtener todas las encuestas (admin)
     */
    public List<EncuestaResponseDTO> obtenerTodasLasEncuestas() {
        return encuestaRepository.findAllByOrderByFechaRespuestaDesc()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener estadísticas de encuestas
     */
    public Map<String, Object> obtenerEstadisticas() {
        List<EncuestaSatisfaccion> encuestas = encuestaRepository.findAll();

        if (encuestas.isEmpty()) {
            Map<String, Object> vacio = new HashMap<>();
            vacio.put("total", 0);
            return vacio;
        }

        Map<String, Object> stats = new HashMap<>();

        // Total de encuestas
        stats.put("total", encuestas.size());

        // Promedios de preguntas principales
        double promedioSatisfaccion = encuestas.stream()
                .mapToInt(EncuestaSatisfaccion::getSatisfaccionGeneral)
                .average()
                .orElse(0.0);

        double promedioExpectativas = encuestas.stream()
                .mapToInt(EncuestaSatisfaccion::getCumplioExpectativas)
                .average()
                .orElse(0.0);

        double promedioRecomendaria = encuestas.stream()
                .mapToInt(EncuestaSatisfaccion::getRecomendaria)
                .average()
                .orElse(0.0);

        double promedioVolveria = encuestas.stream()
                .mapToInt(EncuestaSatisfaccion::getVolveria)
                .average()
                .orElse(0.0);

        stats.put("promedioSatisfaccionGeneral", Math.round(promedioSatisfaccion * 100.0) / 100.0);
        stats.put("promedioCumplioExpectativas", Math.round(promedioExpectativas * 100.0) / 100.0);
        stats.put("promedioRecomendaria", Math.round(promedioRecomendaria * 100.0) / 100.0);
        stats.put("promedioVolveria", Math.round(promedioVolveria * 100.0) / 100.0);

        // NPS Score (basado en "recomendaria")
        long promotores = encuestas.stream()
                .filter(e -> e.getRecomendaria() >= 4)
                .count();

        long detractores = encuestas.stream()
                .filter(e -> e.getRecomendaria() <= 2)
                .count();

        double nps = ((double) (promotores - detractores) / encuestas.size()) * 100;
        stats.put("npsScore", Math.round(nps));

        return stats;
    }

    /**
     * Convertir entidad a DTO
     */
    private EncuestaResponseDTO convertirADTO(EncuestaSatisfaccion encuesta) {
        return EncuestaResponseDTO.builder()
                .id(encuesta.getId())
                .nombreCliente(encuesta.getCliente().getNombre())
                .telefonoCliente(encuesta.getCliente().getTelefono())
                .reservaId(encuesta.getReserva().getId())
                .satisfaccionGeneral(encuesta.getSatisfaccionGeneral())
                .cumplioExpectativas(encuesta.getCumplioExpectativas())
                .recomendaria(encuesta.getRecomendaria())
                .volveria(encuesta.getVolveria())
                .porQueRecomendaria(encuesta.getPorQueRecomendaria())
                .queGusto(encuesta.getQueGusto())
                .queMejorar(encuesta.getQueMejorar())
                .queAgregar(encuesta.getQueAgregar())
                .fechaRespuesta(encuesta.getFechaRespuesta())
                .build();
    }
}