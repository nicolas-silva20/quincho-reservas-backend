package com.elumbral.quincho.controller;

import com.elumbral.quincho.model.dto.ApiResponseDTO;
import com.elumbral.quincho.model.dto.ReservaRequestDTO;
import com.elumbral.quincho.model.dto.ReservaResponseDTO;
import com.elumbral.quincho.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;

    /**
     * Crear nueva reserva
     * POST /api/reservas
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ReservaResponseDTO>> crearReserva(
            @Valid @RequestBody ReservaRequestDTO request) {

        ReservaResponseDTO reserva = reservaService.crearReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(reserva, "Reserva creada exitosamente"));
    }

    /**
     * Obtener reserva por ID
     * GET /api/reservas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReservaResponseDTO>> obtenerReserva(@PathVariable Long id) {
        ReservaResponseDTO reserva = reservaService.obtenerReservaPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.success(reserva, "Reserva encontrada"));
    }

    /**
     * Cancelar reserva (por cliente)
     * DELETE /api/reservas/{id}/cancelar
     */
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponseDTO<ReservaResponseDTO>> cancelarReserva(@PathVariable Long id) {
        ReservaResponseDTO reserva = reservaService.cancelarReserva(id, false);
        return ResponseEntity.ok(ApiResponseDTO.success(reserva, "Reserva cancelada exitosamente"));
    }

    /**
     * Marcar reserva como finalizada y generar token de reseña
     * PUT /api/reservas/{id}/finalizar
     */
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ApiResponseDTO<ReservaResponseDTO>> finalizarReserva(@PathVariable Long id) {
        try {
            ReservaResponseDTO reserva = reservaService.marcarComoFinalizada(id);
            return ResponseEntity.ok(
                    ApiResponseDTO.success(reserva, "Reserva finalizada. Token de reseña generado.")
            );
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }


}