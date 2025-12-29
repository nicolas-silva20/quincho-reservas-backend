package com.elumbral.quincho.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "lista_espera")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaEspera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experiencia_id", nullable = false)
    @NotNull(message = "La experiencia es obligatoria")
    @JsonIgnoreProperties({"items", "reservas"})
    private Experiencia experiencia;

    @Column(name = "fecha_deseada", nullable = false)
    @NotNull(message = "La fecha deseada es obligatoria")
    private LocalDate fechaDeseada;

    @Column(name = "hora_deseada", nullable = false)
    @NotNull(message = "La hora deseada es obligatoria")
    private LocalTime horaDeseada;

    @Column(name = "notificado")
    private Boolean notificado = false;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (notificado == null) {
            notificado = false;
        }
    }
}