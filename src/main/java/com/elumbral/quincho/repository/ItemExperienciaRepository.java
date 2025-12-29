package com.elumbral.quincho.repository;

import com.elumbral.quincho.model.entity.ItemExperiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemExperienciaRepository extends JpaRepository<ItemExperiencia, Long> {

    List<ItemExperiencia> findByExperienciaId(Long experienciaId);
}