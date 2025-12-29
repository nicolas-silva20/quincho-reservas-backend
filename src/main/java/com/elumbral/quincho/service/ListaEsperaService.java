package com.elumbral.quincho.service;

import com.elumbral.quincho.exception.RecursoNoEncontradoException;
import com.elumbral.quincho.model.dto.ListaEsperaRequestDTO;
import com.elumbral.quincho.model.entity.Experiencia;
import com.elumbral.quincho.model.entity.ListaEspera;
import com.elumbral.quincho.repository.ListaEsperaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListaEsperaService {

    private final ListaEsperaRepository listaEsperaRepository;
    private final ExperienciaService experienciaService;

    /**
     * Agregar a lista de espera
     */
    @Transactional
    public ListaEspera agregarAListaEspera(ListaEsperaRequestDTO request) {
        log.info("Agregando a lista de espera: {} para fecha {}", request.getNombreCliente(), request.getFechaDeseada());

        Experiencia experiencia = experienciaService.obtenerPorId(request.getExperienciaId());

        ListaEspera listaEspera = new ListaEspera();
        listaEspera.setNombreCliente(request.getNombreCliente());
        listaEspera.setTelefono(request.getTelefono());
        listaEspera.setEmail(request.getEmail());
        listaEspera.setExperiencia(experiencia);
        listaEspera.setFechaDeseada(request.getFechaDeseada());
        listaEspera.setHoraDeseada(request.getHoraDeseada());
        listaEspera.setNotificado(false);

        ListaEspera guardado = listaEsperaRepository.save(listaEspera);
        log.info("Agregado a lista de espera con ID: {}", guardado.getId());

        return guardado;
    }

    /**
     * Obtener todas las entradas de lista de espera
     */
    public List<ListaEspera> obtenerTodos() {
        return listaEsperaRepository.findAll();
    }

    /**
     * Obtener por fecha deseada
     */
    public List<ListaEspera> obtenerPorFecha(java.time.LocalDate fecha) {
        return listaEsperaRepository.findByFechaDeseada(fecha);
    }

    /**
     * Marcar como notificado
     */
    @Transactional
    public void marcarComoNotificado(Long id) {
        ListaEspera listaEspera = listaEsperaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ListaEspera", id));
        listaEspera.setNotificado(true);
        listaEsperaRepository.save(listaEspera);
    }

    /**
     * Eliminar de lista de espera
     */
    @Transactional
    public void eliminar(Long id) {
        listaEsperaRepository.deleteById(id);
    }
}