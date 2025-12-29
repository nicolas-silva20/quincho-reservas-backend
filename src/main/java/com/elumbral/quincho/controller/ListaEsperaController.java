package com.elumbral.quincho.controller;

import com.elumbral.quincho.model.dto.ApiResponseDTO;
import com.elumbral.quincho.model.dto.ListaEsperaRequestDTO;
import com.elumbral.quincho.model.dto.ListaEsperaResponseDTO;
import com.elumbral.quincho.model.entity.ListaEspera;
import com.elumbral.quincho.service.ListaEsperaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lista-espera")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ListaEsperaController {

    private final ListaEsperaService listaEsperaService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ListaEspera>> agregar(@Valid @RequestBody ListaEsperaRequestDTO request) {
        ListaEspera listaEspera = listaEsperaService.agregarAListaEspera(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(listaEspera, "Agregado a lista de espera exitosamente"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ListaEsperaResponseDTO>>> obtenerTodos() {
        List<ListaEspera> lista = listaEsperaService.obtenerTodos();
        
        List<ListaEsperaResponseDTO> dtos = lista.stream()
                .map(item -> new ListaEsperaResponseDTO(
                        item.getId(),
                        item.getNombreCliente(),
                        item.getTelefono(),
                        item.getEmail(),
                        item.getExperiencia() != null 
                            ? new ListaEsperaResponseDTO.ExperienciaSimpleDTO(
                                item.getExperiencia().getId(),
                                item.getExperiencia().getNombre()
                            )
                            : null,
                        item.getFechaDeseada(),
                        item.getHoraDeseada() != null ? item.getHoraDeseada().toString() : null,
                        item.getNotificado()
                ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponseDTO.success(dtos, "Lista de espera obtenida"));
    }

    @PutMapping("/{id}/notificar")
    public ResponseEntity<ApiResponseDTO<Void>> marcarNotificado(@PathVariable Long id) {
        listaEsperaService.marcarComoNotificado(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "Marcado como notificado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminar(@PathVariable Long id) {
        listaEsperaService.eliminar(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "Eliminado de lista de espera"));
    }
}