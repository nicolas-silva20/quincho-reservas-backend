-- ============================================
-- SCRIPT PARA RAILWAY - RESEÑAS
-- ============================================

-- Insertar clientes necesarios para las reseñas (IDs 9-13 del dump)
INSERT INTO clientes (id, nombre, email, telefono, fecha_registro) VALUES
(9, 'María González', 'maria.gonzalez@email.com', '1154782345', '2025-12-14 03:26:13'),
(10, 'Carlos Fernández', 'carlos.fernandez@email.com', '1165893421', '2025-12-14 03:26:13'),
(11, 'Laura Martínez', 'laura.martinez@email.com', '1143567890', '2025-12-14 03:26:13'),
(12, 'Roberto Silva', 'roberto.silva@email.com', '1156789234', '2025-12-14 03:26:13'),
(13, 'Ana Rodríguez', 'ana.rodriguez@email.com', '1167234589', '2025-12-14 03:26:13');

-- Insertar reseñas aprobadas (sin reserva_id porque son reseñas de ejemplo)
INSERT INTO resenas (id, cliente_id, comentario, calificacion, fecha_creacion, aprobada, fecha_aprobacion, reserva_id) VALUES
(1, 13, 'Increíble experiencia! El lugar es hermoso y la atención fue excelente. Celebramos el cumpleaños de mi hijo y todos quedaron encantados. 100% recomendable.', 5, '2025-12-09 03:26:13', 1, '2025-12-10 03:26:13', NULL),
(2, 12, 'El quincho superó todas nuestras expectativas. Espacioso, limpio y con todas las comodidades. La parrilla es espectacular y el jardín perfecto para los chicos.', 5, '2025-12-02 03:26:13', 1, '2025-12-03 03:26:13', NULL),
(3, 11, 'Muy buen lugar para eventos. Lo usamos para una reunión familiar y estuvo genial. El único detalle es que el acceso puede ser un poco complicado, pero nada grave.', 4, '2025-11-24 03:26:13', 1, '2025-11-25 03:26:13', NULL),
(4, 10, 'Excelente para cumpleaños! Vinimos con 30 personas y había espacio de sobra. La pileta fue un éxito con los chicos. Volveremos seguro.', 5, '2025-11-09 03:26:13', 1, '2025-11-10 03:26:13', NULL),
(5, 9, 'Lugar muy cómodo y bien equipado. Ideal para pasar el día con amigos. La atención al cliente es de primera. Solo le faltaría un poco más de sombra en el sector del quincho.', 4, '2025-12-12 03:26:13', 1, '2025-12-14 06:30:36', NULL);

-- Resetear AUTO_INCREMENT
ALTER TABLE clientes AUTO_INCREMENT = 21;
ALTER TABLE resenas AUTO_INCREMENT = 6;

-- Verificación
SELECT 'Clientes insertados:' as mensaje, COUNT(*) as total FROM clientes WHERE id BETWEEN 9 AND 13;
SELECT 'Reseñas insertadas:' as mensaje, COUNT(*) as total FROM resenas WHERE aprobada = 1;
