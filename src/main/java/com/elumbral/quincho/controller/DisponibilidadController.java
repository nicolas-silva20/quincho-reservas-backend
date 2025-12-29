package com.elumbral.quincho.controller;

import com.elumbral.quincho.model.dto.ApiResponseDTO;
import com.elumbral.quincho.model.dto.DisponibilidadDTO;
import com.elumbral.quincho.service.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    /**
     * Verificar disponibilidad de una fecha y hora específica
     * GET /api/disponibilidad/verificar?fecha=2025-12-25&hora=14:00
     */
    @GetMapping("/verificar")
    public ResponseEntity<ApiResponseDTO<DisponibilidadDTO>> verificarDisponibilidad(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora) {

        DisponibilidadDTO resultado = disponibilidadService.verificarDisponibilidad(fecha, hora);

        return ResponseEntity.ok(ApiResponseDTO.success(resultado, "Disponibilidad verificada"));
    }
}