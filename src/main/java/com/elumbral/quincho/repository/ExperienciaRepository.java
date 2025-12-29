package com.elumbral.quincho.repository;

import com.elumbral.quincho.model.entity.Experiencia;
import com.elumbral.quincho.model.enums.TipoExperiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Long> {

    List<Experiencia> findByActivoTrue();

    Optional<Experiencia> findByTipo(TipoExperiencia tipo);

    List<Experiencia> findByTipoAndActivoTrue(TipoExperiencia tipo);
}