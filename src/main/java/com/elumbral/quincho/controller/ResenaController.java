package com.elumbral.quincho.controller;

import com.elumbral.quincho.model.dto.ApiResponseDTO;
import com.elumbral.quincho.model.dto.ResenaDTO;
import com.elumbral.quincho.model.dto.ResenaRequestDTO;
import com.elumbral.quincho.service.ResenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResenaController {

    private final ResenaService resenaService;

    /**
     * Obtener reseñas destacadas (últimas 3 aprobadas)
     * GET /api/resenas/destacadas
     */
    @GetMapping("/destacadas")
    public ResponseEntity<ApiResponseDTO<List<ResenaDTO>>> obtenerResenasDestacadas() {
        List<ResenaDTO> resenas = resenaService.obtenerResenasDestacadas();
        return ResponseEntity.ok(ApiResponseDTO.success(resenas, "Reseñas destacadas obtenidas"));
    }

    /**
     * Obtener todas las reseñas aprobadas
     * GET /api/resenas
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ResenaDTO>>> obtenerResenasAprobadas() {
        List<ResenaDTO> resenas = resenaService.obtenerResenasAprobadas();
        return ResponseEntity.ok(ApiResponseDTO.success(resenas, "Reseñas obtenidas exitosamente"));
    }

    /**
     * Crear nueva reseña con token
     * POST /api/resenas/crear
     */
    @PostMapping("/crear")
    public ResponseEntity<ApiResponseDTO<ResenaDTO>> crearResena(@Valid @RequestBody ResenaRequestDTO request) {
        try {
            ResenaDTO resena = resenaService.crearResenaConToken(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success(resena, "Reseña creada exitosamente. Será revisada antes de publicarse."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    /**
     * ADMIN: Obtener todas las reseñas (aprobadas y pendientes)
     * GET /api/resenas/admin/todas
     */
    @GetMapping("/admin/todas")
    public ResponseEntity<ApiResponseDTO<List<ResenaDTO>>> obtenerTodasLasResenas() {
        List<ResenaDTO> resenas = resenaService.obtenerTodasLasResenas();
        return ResponseEntity.ok(ApiResponseDTO.success(resenas, "Todas las reseñas obtenidas"));
    }

    /**
     * ADMIN: Aprobar reseña
     * PUT /api/resenas/admin/{id}/aprobar
     */
    @PutMapping("/admin/{id}/aprobar")
    public ResponseEntity<ApiResponseDTO<ResenaDTO>> aprobarResena(@PathVariable Long id) {
        try {
            ResenaDTO resena = resenaService.aprobarResena(id);
            return ResponseEntity.ok(ApiResponseDTO.success(resena, "Reseña aprobada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    /**
     * ADMIN: Rechazar/Ocultar reseña
     * PUT /api/resenas/admin/{id}/rechazar
     */
    @PutMapping("/admin/{id}/rechazar")
    public ResponseEntity<ApiResponseDTO<ResenaDTO>> rechazarResena(@PathVariable Long id) {
        try {
            ResenaDTO resena = resenaService.rechazarResena(id);
            return ResponseEntity.ok(ApiResponseDTO.success(resena, "Reseña rechazada"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    /**
     * ADMIN: Eliminar reseña y retornar datos de encuesta
     * DELETE /api/resenas/admin/{id}
     */
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponseDTO<ResenaDTO>> eliminarResena(@PathVariable Long id) {
        try {
            log.info("🔍 DEBUG: Intentando eliminar reseña ID: {}", id);
            ResenaDTO resenaDTO = resenaService.eliminarResena(id);
            log.info("🔍 DEBUG: DTO recibido del servicio: {}", resenaDTO);
            log.info("🔍 DEBUG: Token en DTO: {}", resenaDTO != null ? resenaDTO.getEncuestaToken() : "NULL");
            
            ApiResponseDTO<ResenaDTO> response = ApiResponseDTO.success(resenaDTO, "Reseña eliminada");
            log.info("🔍 DEBUG: Response a enviar: {}", response);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("❌ Error al eliminar reseña: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }
}