package com.elumbral.quincho.repository;

import com.elumbral.quincho.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Métodos que devuelven listas (pueden haber múltiples clientes con mismo teléfono/email)
    List<Cliente> findAllByTelefono(String telefono);
    List<Cliente> findAllByEmail(String email);

    // Métodos opcionales (por si los necesitas en el futuro)
    Optional<Cliente> findByTelefono(String telefono);  // Solo el primero
    Optional<Cliente> findByEmail(String email);        // Solo el primero
}