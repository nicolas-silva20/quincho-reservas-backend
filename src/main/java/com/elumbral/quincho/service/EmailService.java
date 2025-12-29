package com.elumbral.quincho.service;

import com.elumbral.quincho.model.entity.Reserva;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    private static final String EMAIL_ORIGEN = "noreply@elumbral.com";

    /**
     * Enviar email de confirmación de reserva
     */
    public void enviarEmailConfirmacionReserva(Reserva reserva) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(EMAIL_ORIGEN);
            mensaje.setTo(reserva.getCliente().getEmail());
            mensaje.setSubject("Confirmación de Reserva - El Umbral");
            mensaje.setText(construirMensajeConfirmacion(reserva));

            mailSender.send(mensaje);
            log.info("Email de confirmación enviado a: {}", reserva.getCliente().getEmail());
        } catch (Exception e) {
            log.error("Error al enviar email de confirmación: {}", e.getMessage());
        }
    }

    /**
     * Enviar email de cancelación
     */
    public void enviarEmailCancelacionReserva(Reserva reserva) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(EMAIL_ORIGEN);
            mensaje.setTo(reserva.getCliente().getEmail());
            mensaje.setSubject("Cancelación de Reserva - El Umbral");
            mensaje.setText(construirMensajeCancelacion(reserva));

            mailSender.send(mensaje);
            log.info("Email de cancelación enviado a: {}", reserva.getCliente().getEmail());
        } catch (Exception e) {
            log.error("Error al enviar email de cancelación: {}", e.getMessage());
        }
    }

    private String construirMensajeConfirmacion(Reserva reserva) {
        return String.format("""
            Hola %s,
            
            ¡Tu reserva ha sido pre-confirmada exitosamente!
            
            Detalles de tu reserva:
            - Experiencia: %s
            - Fecha: %s
            - Hora: %s
            - Precio total: $%s
            - Número de reserva: #%d
            
            Próximos pasos:
            Nos comunicaremos contigo en el horario indicado (%s) para coordinar tu visita 
            al predio y el pago de la seña.
            
            Recordá que tenés 48 horas desde esta confirmación para realizar la visita 
            y completar el pago.
            
            ¡Gracias por elegirnos!
            
            Saludos,
            El equipo de El Umbral
            """,
                reserva.getCliente().getNombre(),
                reserva.getExperiencia().getNombre(),
                reserva.getFechaEvento(),
                reserva.getHoraInicio(),
                reserva.getPrecioTotal(),
                reserva.getId(),
                reserva.getHorarioContacto()
        );
    }

    private String construirMensajeCancelacion(Reserva reserva) {
        return String.format("""
            Hola %s,
            
            Te informamos que tu reserva ha sido cancelada.
            
            Detalles de la reserva cancelada:
            - Experiencia: %s
            - Fecha: %s
            - Hora: %s
            - Número de reserva: #%d
            
            Si tenés alguna consulta, no dudes en contactarnos.
            
            Saludos,
            El equipo de El Umbral
            """,
                reserva.getCliente().getNombre(),
                reserva.getExperiencia().getNombre(),
                reserva.getFechaEvento(),
                reserva.getHoraInicio(),
                reserva.getId()
        );
    }
}