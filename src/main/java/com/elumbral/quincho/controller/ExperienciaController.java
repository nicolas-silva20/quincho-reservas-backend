package com.elumbral.quincho.controller;

import com.elumbral.quincho.model.dto.ApiResponseDTO;
import com.elumbral.quincho.model.entity.Experiencia;
import com.elumbral.quincho.model.entity.ItemExperiencia;
import com.elumbral.quincho.service.ExperienciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiencias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExperienciaController {

    private final ExperienciaService experienciaService;

    /**
     * Obtener todas las experiencias activas
     * GET /api/experiencias
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Experiencia>>> obtenerExperienciasActivas() {
        List<Experiencia> experiencias = experienciaService.obtenerExperienciasActivas();
        return ResponseEntity.ok(ApiResponseDTO.success(experiencias, "Experiencias obtenidas exitosamente"));
    }

    /**
     * Obtener experiencia por ID
     * GET /api/experiencias/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Experiencia>> obtenerExperienciaPorId(@PathVariable Long id) {
        Experiencia experiencia = experienciaService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.success(experiencia, "Experiencia encontrada"));
    }

    /**
     * Obtener items de una experiencia
     * GET /api/experiencias/{id}/items
     */
    @GetMapping("/{id}/items")
    public ResponseEntity<ApiResponseDTO<List<ItemExperiencia>>> obtenerItems(@PathVariable Long id) {
        List<ItemExperiencia> items = experienciaService.obtenerItemsPorExperiencia(id);
        return ResponseEntity.ok(ApiResponseDTO.success(items, "Items obtenidos exitosamente"));
    }
}