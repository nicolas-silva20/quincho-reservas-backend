package com.elumbral.quincho.service;

import com.elumbral.quincho.exception.RecursoNoEncontradoException;
import com.elumbral.quincho.model.entity.Cliente;
import com.elumbral.quincho.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Buscar o crear cliente
     * IMPORTANTE: Siempre crea un nuevo cliente para cada reserva
     * Esto permite que múltiples personas con el mismo teléfono tengan reservas separadas
     */
    @Transactional
    public Cliente buscarOCrearCliente(String nombre, String telefono, String email) {
        // SIEMPRE crear un nuevo cliente (no buscar por teléfono)
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setEmail(email);

        Cliente clienteGuardado = clienteRepository.save(nuevoCliente);
        log.info("Cliente creado con ID: {}", clienteGuardado.getId());

        return clienteGuardado;
    }

    /**
     * Obtener cliente por ID
     */
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", id));
    }

    /**
     * Obtener todos los clientes
     */
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Buscar clientes por teléfono (puede haber múltiples)
     */
    public List<Cliente> buscarPorTelefono(String telefono) {
        return clienteRepository.findAllByTelefono(telefono);
    }

    /**
     * Buscar cliente por email (puede haber múltiples)
     */
    public List<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findAllByEmail(email);
    }

    /**
     * Actualizar cliente
     */
    @Transactional
    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        Cliente cliente = obtenerPorId(id);

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setEmail(clienteActualizado.getEmail());
        cliente.setTelefono(clienteActualizado.getTelefono());

        return clienteRepository.save(cliente);
    }

    /**
     * Eliminar cliente
     */
    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        clienteRepository.delete(cliente);
        log.info("Cliente {} eliminado", id);
    }
}