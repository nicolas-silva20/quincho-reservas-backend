package com.elumbral.quincho.controller;

import com.elumbral.quincho.model.dto.ApiResponseDTO;
import com.elumbral.quincho.model.dto.EncuestaRequestDTO;
import com.elumbral.quincho.model.dto.EncuestaResponseDTO;
import com.elumbral.quincho.service.EncuestaSatisfaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/encuestas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class EncuestaSatisfaccionController {

    private final EncuestaSatisfaccionService encuestaService;

    /**
     * Crear encuesta de satisfacción con token
     * POST /api/encuestas/crear
     */
    @PostMapping("/crear")
    public ResponseEntity<ApiResponseDTO<EncuestaResponseDTO>> crearEncuesta(
            @Valid @RequestBody EncuestaRequestDTO request) {
        try {
            EncuestaResponseDTO encuesta = encuestaService.crearEncuestaConToken(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success(encuesta, "Encuesta creada exitosamente. ¡Gracias por tu feedback!"));
        } catch (IllegalArgumentException e) {
            log.error("Error al crear encuesta: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Error inesperado al crear encuesta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Error al crear la encuesta"));
        }
    }

    /**
     * Obtener todas las encuestas (admin)
     * GET /api/encuestas/todas
     */
    @GetMapping("/todas")
    public ResponseEntity<ApiResponseDTO<List<EncuestaResponseDTO>>> obtenerTodasLasEncuestas() {
        try {
            List<EncuestaResponseDTO> encuestas = encuestaService.obtenerTodasLasEncuestas();
            return ResponseEntity.ok(ApiResponseDTO.success(encuestas, "Encuestas obtenidas exitosamente"));
        } catch (Exception e) {
            log.error("Error al obtener encuestas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Error al obtener las encuestas"));
        }
    }

    /**
     * Obtener estadísticas de encuestas (admin)
     * GET /api/encuestas/estadisticas
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> obtenerEstadisticas() {
        try {
            Map<String, Object> estadisticas = encuestaService.obtenerEstadisticas();
            return ResponseEntity.ok(ApiResponseDTO.success(estadisticas, "Estadísticas obtenidas exitosamente"));
        } catch (Exception e) {
            log.error("Error al obtener estadísticas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Error al obtener las estadísticas"));
        }
    }
}
