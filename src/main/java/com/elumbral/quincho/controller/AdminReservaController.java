package com.elumbral.quincho.controller;

import com.elumbral.quincho.model.dto.ApiResponseDTO;
import com.elumbral.quincho.model.dto.ReservaResponseDTO;
import com.elumbral.quincho.model.enums.EstadoPago;
import com.elumbral.quincho.model.enums.EstadoReserva;
import com.elumbral.quincho.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminReservaController {

    private final ReservaService reservaService;

    /**
     * Obtener todas las reservas (para administrador)
     * GET /api/admin/reservas
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ReservaResponseDTO>>> obtenerTodasLasReservas() {
        List<ReservaResponseDTO> reservas = reservaService.obtenerTodasLasReservas();
        return ResponseEntity.ok(ApiResponseDTO.success(reservas, "Reservas obtenidas exitosamente"));
    }

    /**
     * Actualizar estado de una reserva
     * PUT /api/admin/reservas/{id}/estado
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponseDTO<ReservaResponseDTO>> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        EstadoReserva nuevoEstado = EstadoReserva.valueOf(body.get("estado"));
        ReservaResponseDTO reserva = reservaService.actualizarEstado(id, nuevoEstado);

        return ResponseEntity.ok(ApiResponseDTO.success(reserva, "Estado actualizado exitosamente"));
    }

    /**
     * Actualizar estado de pago
     * PUT /api/admin/reservas/{id}/estado-pago
     */
    @PutMapping("/{id}/estado-pago")
    public ResponseEntity<ApiResponseDTO<ReservaResponseDTO>> actualizarEstadoPago(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        EstadoPago nuevoEstadoPago = EstadoPago.valueOf(body.get("estadoPago"));
        ReservaResponseDTO reserva = reservaService.actualizarEstadoPago(id, nuevoEstadoPago);

        return ResponseEntity.ok(ApiResponseDTO.success(reserva, "Estado de pago actualizado"));
    }

    /**
     * Cancelar reserva (por administrador)
     * DELETE /api/admin/reservas/{id}/cancelar
     */
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponseDTO<ReservaResponseDTO>> cancelarReserva(@PathVariable Long id) {
        ReservaResponseDTO reserva = reservaService.cancelarReserva(id, true);
        return ResponseEntity.ok(ApiResponseDTO.success(reserva, "Reserva cancelada por administrador"));
    }
}