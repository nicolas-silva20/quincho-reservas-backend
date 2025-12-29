package com.elumbral.quincho.repository;

import com.elumbral.quincho.model.entity.ListaEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ListaEsperaRepository extends JpaRepository<ListaEspera, Long> {
    List<ListaEspera> findByFechaDeseada(LocalDate fechaDeseada);
    List<ListaEspera> findByNotificadoFalse();
}