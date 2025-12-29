package com.elumbral.quincho.service;

import com.elumbral.quincho.exception.RecursoNoEncontradoException;
import com.elumbral.quincho.model.entity.Experiencia;
import com.elumbral.quincho.model.entity.ItemExperiencia;
import com.elumbral.quincho.model.enums.TipoExperiencia;
import com.elumbral.quincho.repository.ExperienciaRepository;
import com.elumbral.quincho.repository.ItemExperienciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienciaService {

    private final ExperienciaRepository experienciaRepository;
    private final ItemExperienciaRepository itemExperienciaRepository;

    public List<Experiencia> obtenerExperienciasActivas() {
        return experienciaRepository.findByActivoTrue();
    }

    public Experiencia obtenerPorId(Long id) {
        return experienciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Experiencia", id));
    }

    public Experiencia obtenerPorTipo(TipoExperiencia tipo) {
        return experienciaRepository.findByTipo(tipo)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Experiencia tipo " + tipo + " no encontrada"));
    }

    public List<ItemExperiencia> obtenerItemsPorExperiencia(Long experienciaId) {
        return itemExperienciaRepository.findByExperienciaId(experienciaId);
    }

    /**
     * Calcula el precio total basado en items seleccionados (para experiencia personalizada)
     */
    public BigDecimal calcularPrecioPersonalizado(Long experienciaId, List<Long> itemsSeleccionados) {
        Experiencia experiencia = obtenerPorId(experienciaId);

        if (experiencia.getPrecioFijo()) {
            return experiencia.getPrecioBase();
        }

        BigDecimal precioTotal = experiencia.getPrecioBase();

        List<ItemExperiencia> items = obtenerItemsPorExperiencia(experienciaId);

        for (ItemExperiencia item : items) {
            if (itemsSeleccionados.contains(item.getId()) &&
                    item.getCostoAdicional() != null &&
                    item.getCostoAdicional().compareTo(BigDecimal.ZERO) > 0) {
                precioTotal = precioTotal.add(item.getCostoAdicional());
            }
        }

        return precioTotal;
    }

    @Transactional
    public Experiencia crearExperiencia(Experiencia experiencia) {
        return experienciaRepository.save(experiencia);
    }

    @Transactional
    public Experiencia actualizarExperiencia(Long id, Experiencia experienciaActualizada) {
        Experiencia experiencia = obtenerPorId(id);

        experiencia.setNombre(experienciaActualizada.getNombre());
        experiencia.setPrecioBase(experienciaActualizada.getPrecioBase());
        experiencia.setDescripcion(experienciaActualizada.getDescripcion());
        experiencia.setActivo(experienciaActualizada.getActivo());

        return experienciaRepository.save(experiencia);
    }
}