package com.elumbral.quincho.repository;

import com.elumbral.quincho.model.entity.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByAprobadaTrue();

    List<Resena> findByAprobadaFalse();

    List<Resena> findTop3ByAprobadaTrueOrderByFechaCreacionDesc();

    // ========================================
    // NUEVOS MÉTODOS PARA ADMINISTRACIÓN
    // ========================================

    List<Resena> findAllByOrderByFechaCreacionDesc();

    List<Resena> findByAprobadaTrueOrderByFechaCreacionDesc();
}