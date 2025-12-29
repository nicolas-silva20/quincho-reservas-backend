package com.elumbral.quincho.service;

import com.elumbral.quincho.exception.FechaNoDisponibleException;
import com.elumbral.quincho.exception.RecursoNoEncontradoException;
import com.elumbral.quincho.exception.ReservaException;
import com.elumbral.quincho.model.dto.ReservaRequestDTO;
import com.elumbral.quincho.model.dto.ReservaResponseDTO;
import com.elumbral.quincho.model.entity.Cliente;
import com.elumbral.quincho.model.entity.Experiencia;
import com.elumbral.quincho.model.entity.Reserva;
import com.elumbral.quincho.model.enums.EstadoPago;
import com.elumbral.quincho.model.enums.EstadoReserva;
import com.elumbral.quincho.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteService clienteService;
    private final ExperienciaService experienciaService;
    private final DisponibilidadService disponibilidadService;
    private final EmailService emailService;

    private static final BigDecimal DEPOSITO_GARANTIA = new BigDecimal("100000");

    /**
     * Crear una nueva reserva
     */
    @Transactional
    public ReservaResponseDTO crearReserva(ReservaRequestDTO request) {
        log.info("Creando reserva para cliente: {}", request.getNombreCliente());

        // Validar términos y condiciones
        if (!request.getTerminosAceptados()) {
            throw new ReservaException("Debe aceptar los términos y condiciones");
        }

        // Verificar disponibilidad
        if (!disponibilidadService.estaDisponible(request.getFechaEvento(), request.getHoraInicio())) {
            throw new FechaNoDisponibleException(request.getFechaEvento(), request.getHoraInicio());
        }

        // Buscar o crear cliente
        Cliente cliente = clienteService.buscarOCrearCliente(
                request.getNombreCliente(),
                request.getTelefono(),
                request.getEmail()
        );

        // Obtener experiencia
        Experiencia experiencia = experienciaService.obtenerPorId(request.getExperienciaId());

        // Calcular precios - usar los del request si están disponibles

        BigDecimal precioExperiencia;
        if (request.getPrecioTotal() != null && request.getPrecioTotal() > 0) {
            // Usar el precio calculado en el frontend
            precioExperiencia = BigDecimal.valueOf(request.getPrecioTotal());
        } else {
            // Fallback al precio de la experiencia
            precioExperiencia = experiencia.getPrecioFijo()
                    ? experiencia.getPrecioBase()
                    : experiencia.getPrecioBase();
        }
BigDecimal precioTotal = precioExperiencia.add(DEPOSITO_GARANTIA);

        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setExperiencia(experiencia);
        reserva.setFechaEvento(request.getFechaEvento());
        reserva.setHoraInicio(request.getHoraInicio());
        reserva.setHorarioContacto(request.getHorarioContacto());
        reserva.setPrecioExperiencia(precioExperiencia);
        reserva.setDepositoGarantia(DEPOSITO_GARANTIA);
        reserva.setPrecioTotal(precioTotal);
        reserva.setEstado(EstadoReserva.PRE_CONFIRMADA);
        reserva.setEstadoPago(EstadoPago.PENDIENTE);
        reserva.setTerminosAceptados(true);
        reserva.setObservaciones(request.getObservaciones());

        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Enviar email de confirmación (DESHABILITADO - El admin contactará manualmente)
        // emailService.enviarEmailConfirmacionReserva(reservaGuardada);

        log.info("Reserva creada exitosamente con ID: {}", reservaGuardada.getId());

        return convertirADTO(reservaGuardada);
    }

    /**
     * Obtener reserva por ID
     */
    public ReservaResponseDTO obtenerReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva", id));
        return convertirADTO(reserva);
    }

    /**
     * Obtener todas las reservas
     */
    public List<ReservaResponseDTO> obtenerTodasLasReservas() {
        return reservaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener reservas por cliente
     */
    public List<ReservaResponseDTO> obtenerReservasPorCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener reservas por estado
     */
    public List<ReservaResponseDTO> obtenerReservasPorEstado(EstadoReserva estado) {
        return reservaRepository.findByEstado(estado).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener reservas por rango de fechas
     */
    public List<ReservaResponseDTO> obtenerReservasPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return reservaRepository.findReservasByRangoFechas(inicio, fin).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Cancelar reserva (por cliente o administrador)
     */
    @Transactional
    public ReservaResponseDTO cancelarReserva(Long id, boolean esPorAdmin) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva", id));

        // Validar que se puede cancelar
        if (reserva.getEstado() == EstadoReserva.FINALIZADA ||
                reserva.getEstado() == EstadoReserva.CANCELADA_CLIENTE ||
                reserva.getEstado() == EstadoReserva.CANCELADA_ADMIN) {
            throw new ReservaException("Esta reserva no puede ser cancelada");
        }

        // Cambiar estado según quién cancela
        reserva.setEstado(esPorAdmin ? EstadoReserva.CANCELADA_ADMIN : EstadoReserva.CANCELADA_CLIENTE);

        Reserva reservaActualizada = reservaRepository.save(reserva);

        // Enviar email de cancelación (DESHABILITADO - El admin contactará manualmente)
        // emailService.enviarEmailCancelacionReserva(reservaActualizada);

        // Notificar a lista de espera
        // TODO: Implementar notificación a lista de espera

        log.info("Reserva {} cancelada por {}", id, esPorAdmin ? "administrador" : "cliente");

        return convertirADTO(reservaActualizada);
    }

    /**
     * Actualizar estado de reserva
     */
    @Transactional
    public ReservaResponseDTO actualizarEstado(Long id, EstadoReserva nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva", id));

        reserva.setEstado(nuevoEstado);
        Reserva reservaActualizada = reservaRepository.save(reserva);

        log.info("Estado de reserva {} actualizado a {}", id, nuevoEstado);

        return convertirADTO(reservaActualizada);
    }

    /**
     * Actualizar estado de pago
     */
    @Transactional
    public ReservaResponseDTO actualizarEstadoPago(Long id, EstadoPago nuevoEstadoPago) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva", id));

        reserva.setEstadoPago(nuevoEstadoPago);

        // Si pagó la seña, cambiar estado a CONFIRMADA
        if (nuevoEstadoPago == EstadoPago.SENA_PAGADA) {
            reserva.setEstado(EstadoReserva.CONFIRMADA);
        }

        Reserva reservaActualizada = reservaRepository.save(reserva);

        log.info("Estado de pago de reserva {} actualizado a {}", id, nuevoEstadoPago);

        return convertirADTO(reservaActualizada);
    }

    /**
     * Marcar reserva como finalizada y generar token de reseña
     */
    @Transactional
    public ReservaResponseDTO marcarComoFinalizada(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva", id));

        // Validar que esté en un estado válido para finalizar
        if (reserva.getEstado() == EstadoReserva.CANCELADA_CLIENTE || reserva.getEstado() == EstadoReserva.CANCELADA_ADMIN) { throw new ReservaException("No se puede finalizar una reserva cancelada"); }

        // Cambiar estado
        reserva.setEstado(EstadoReserva.FINALIZADA);

        // Generar token único para reseña
        String token = UUID.randomUUID().toString();
        reserva.setResenaToken(token);
        reserva.setTokenUsado(false);

        Reserva reservaActualizada = reservaRepository.save(reserva);

        log.info("Reserva {} finalizada. Token generado: {}", id, token);
        log.info("Link de reseña: http://localhost:5500/dejar-resena.html?token={}", token);

        return convertirADTO(reservaActualizada);
    }

    /**
     * Convertir entidad a DTO
     */
    private ReservaResponseDTO convertirADTO(Reserva reserva) {
        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .nombreCliente(reserva.getCliente().getNombre())
                .telefonoCliente(reserva.getCliente().getTelefono())
                .emailCliente(reserva.getCliente().getEmail())
                .nombreExperiencia(reserva.getExperiencia().getNombre())
                .fechaEvento(reserva.getFechaEvento())
                .horaInicio(reserva.getHoraInicio())
                .precioExperiencia(reserva.getPrecioExperiencia())
                .depositoGarantia(reserva.getDepositoGarantia())
                .precioTotal(reserva.getPrecioTotal())
                .estado(reserva.getEstado())
                .estadoPago(reserva.getEstadoPago())
                .fechaCreacion(reserva.getFechaCreacion())
                .horarioContacto(reserva.getHorarioContacto())
                .observaciones(reserva.getObservaciones())
                .resenaToken(reserva.getResenaToken())
                .tokenUsado(reserva.getTokenUsado())
                .build();
    }
}

