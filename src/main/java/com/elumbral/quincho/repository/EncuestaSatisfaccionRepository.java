package com.elumbral.quincho.repository;

import com.elumbral.quincho.model.entity.EncuestaSatisfaccion;
import com.elumbral.quincho.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncuestaSatisfaccionRepository extends JpaRepository<EncuestaSatisfaccion, Long> {
    
    List<EncuestaSatisfaccion> findAllByOrderByFechaRespuestaDesc();
    
    Optional<EncuestaSatisfaccion> findByReserva(Reserva reserva);
    
    boolean existsByReserva(Reserva reserva);
}
