package com.elumbral.quincho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class QuinchoReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuinchoReservasApplication.class, args);
		System.out.println("\n========================================");
		System.out.println("🏡 El Umbral - Sistema de Reservas");
		System.out.println("✅ Aplicación iniciada correctamente");
		System.out.println("📍 API disponible en: http://localhost:8080/api");
		System.out.println("========================================\n");
	}
}