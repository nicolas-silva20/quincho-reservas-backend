package com.elumbral.quincho.model.dto;

import java.time.LocalDate;

public class ListaEsperaResponseDTO {
    private Long id;
    private String nombreCliente;
    private String telefono;
    private String email;
    private ExperienciaSimpleDTO experiencia;
    private LocalDate fechaDeseada;
    private String horaDeseada;
    private boolean notificado;

    public static class ExperienciaSimpleDTO {
        private Long id;
        private String nombre;

        public ExperienciaSimpleDTO(Long id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public Long getId() { return id; }
        public String getNombre() { return nombre; }
    }

    public ListaEsperaResponseDTO(Long id, String nombreCliente, String telefono, String email,
                                   ExperienciaSimpleDTO experiencia, LocalDate fechaDeseada,
                                   String horaDeseada, boolean notificado) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.telefono = telefono;
        this.email = email;
        this.experiencia = experiencia;
        this.fechaDeseada = fechaDeseada;
        this.horaDeseada = horaDeseada;
        this.notificado = notificado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public ExperienciaSimpleDTO getExperiencia() { return experiencia; }
    public void setExperiencia(ExperienciaSimpleDTO experiencia) { this.experiencia = experiencia; }
    public LocalDate getFechaDeseada() { return fechaDeseada; }
    public void setFechaDeseada(LocalDate fechaDeseada) { this.fechaDeseada = fechaDeseada; }
    public String getHoraDeseada() { return horaDeseada; }
    public void setHoraDeseada(String horaDeseada) { this.horaDeseada = horaDeseada; }
    public boolean isNotificado() { return notificado; }
    public void setNotificado(boolean notificado) { this.notificado = notificado; }
}