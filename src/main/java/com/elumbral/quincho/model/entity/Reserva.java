package com.elumbral.quincho.model.entity;

import com.elumbral.quincho.model.enums.EstadoPago;
import com.elumbral.quincho.model.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "experiencia_id", nullable = false)
    private Experiencia experiencia;

    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "horario_contacto", length = 50)
    private String horarioContacto;

    @Column(name = "precio_experiencia", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioExperiencia;

    @Column(name = "deposito_garantia", nullable = false, precision = 10, scale = 2)
    private BigDecimal depositoGarantia;

    @Column(name = "precio_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_visita")
    private LocalDateTime fechaVisita;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "terminos_aceptados")
    private Boolean terminosAceptados = false;

    // ========================================
    // NUEVOS CAMPOS PARA SISTEMA DE RESEÑAS
    // ========================================

    @Column(name = "resena_token", unique = true, length = 255)
    private String resenaToken;

    @Column(name = "token_usado")
    private Boolean tokenUsado = false;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoReserva.PENDIENTE;
        }
        if (estadoPago == null) {
            estadoPago = EstadoPago.PENDIENTE;
        }
        if (tokenUsado == null) {
            tokenUsado = false;
        }
    }
}