-- Datos iniciales para el sistema

-- ============================================
-- EXPERIENCIAS
-- ============================================

INSERT INTO experiencias (id, nombre, tipo, precio_base, precio_fijo, descripcion, activo)
VALUES
    (1, 'ESTÁNDAR', 'ESTANDAR', 15000.00, true,
     'Paquete básico con las comodidades esenciales para tu evento', true),

    (2, 'PERSONALIZADA', 'PERSONALIZADA', 15000.00, false,
     'Armá tu propia experiencia seleccionando los extras que necesites', true),

    (3, 'PROMOCIÓN - MES DICIEMBRE', 'PROMOCION', 20000.00, true,
     'Paquete especial del mes con servicios premium a precio promocional', true);

-- ============================================
-- ITEMS DE EXPERIENCIA - ESTÁNDAR
-- ============================================

INSERT INTO items_experiencia (experiencia_id, nombre, incluido, costo_adicional, obligatorio)
VALUES
-- Estándar
(1, 'Parrilla completa', true, 0.00, false),
(1, 'Mesa y sillas (8 personas)', true, 0.00, false),
(1, 'Baño completo', true, 0.00, false),
(1, 'Iluminación exterior', true, 0.00, false),
(1, 'Horno de barro', false, 0.00, false),
(1, 'Pileta climatizada', false, 0.00, false),
(1, 'Barra con heladera', false, 0.00, false),
(1, 'Fogón', false, 0.00, false),
(1, 'Sistema de música ambiente', false, 0.00, false),
(1, 'Quincho techado', false, 0.00, false);

-- ============================================
-- ITEMS DE EXPERIENCIA - PERSONALIZADA
-- ============================================

INSERT INTO items_experiencia (experiencia_id, nombre, incluido, costo_adicional, obligatorio)
VALUES
-- Personalizada (con costos adicionales)
(2, 'Parrilla completa', true, 0.00, true),
(2, 'Mesa y sillas (8 personas)', true, 0.00, true),
(2, 'Baño completo', true, 0.00, true),
(2, 'Iluminación exterior', true, 0.00, true),
(2, 'Horno de barro', false, 3000.00, false),
(2, 'Pileta climatizada', false, 5000.00, false),
(2, 'Barra con heladera', false, 2500.00, false),
(2, 'Fogón', false, 2000.00, false),
(2, 'Sistema de música ambiente', false, 3500.00, false),
(2, 'Quincho techado', false, 4000.00, false);

-- ============================================
-- ITEMS DE EXPERIENCIA - PROMOCIÓN
-- ============================================

INSERT INTO items_experiencia (experiencia_id, nombre, incluido, costo_adicional, obligatorio)
VALUES
-- Promoción Diciembre
(3, 'Parrilla completa', true, 0.00, false),
(3, 'Mesa y sillas (8 personas)', true, 0.00, false),
(3, 'Baño completo', true, 0.00, false),
(3, 'Iluminación exterior', true, 0.00, false),
(3, 'Horno de barro', true, 0.00, false),
(3, 'Pileta climatizada', true, 0.00, false),
(3, 'Barra con heladera', true, 0.00, false),
(3, 'Fogón', false, 0.00, false),
(3, 'Sistema de música ambiente', true, 0.00, false),
(3, 'Quincho techado', false, 0.00, false);

-- ============================================
-- CLIENTES DE EJEMPLO (OPCIONAL)
-- ============================================

INSERT INTO clientes (nombre, email, telefono, fecha_registro)
VALUES
    ('Juan Pérez', 'juan.perez@email.com', '+54911234567', NOW()),
    ('María González', 'maria.gonzalez@email.com', '+54911234568', NOW()),
    ('Carlos Rodríguez', 'carlos.rodriguez@email.com', '+54911234569', NOW());

-- ============================================
-- RESEÑAS DE EJEMPLO
-- ============================================

INSERT INTO resenas (cliente_id, comentario, calificacion, fecha_creacion, aprobada, fecha_aprobacion)
VALUES
    (1, 'Una experiencia increíble. El lugar es hermoso y las instalaciones están impecables. Definitivamente volveremos!',
     5, '2024-10-15 10:30:00', true, '2024-10-15 14:00:00'),

    (2, 'Excelente atención y el quincho superó nuestras expectativas. La parrilla es de primera y la pileta climatizada fue un plus genial.',
     5, '2024-11-20 15:45:00', true, '2024-11-20 18:00:00'),

    (3, 'Muy lindo lugar para pasar el día. La ubicación es tranquila y tiene todo lo necesario. Muy recomendable!',
     4, '2024-12-01 12:00:00', true, '2024-12-01 16:00:00');

-- ============================================
-- RESETEAR AUTO INCREMENT
-- ============================================

ALTER TABLE experiencias AUTO_INCREMENT = 4;
ALTER TABLE items_experiencia AUTO_INCREMENT = 31;
ALTER TABLE clientes AUTO_INCREMENT = 4;
ALTER TABLE resenas AUTO_INCREMENT = 4;