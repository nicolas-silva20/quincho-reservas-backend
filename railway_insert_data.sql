-- ============================================
-- SCRIPT PARA RAILWAY - DATOS INICIALES
-- Basado en dump local del 2025-12-29
-- ============================================

-- Limpiar tablas existentes (por si acaso)
DELETE FROM items_experiencia WHERE experiencia_id IN (1,2,3);
DELETE FROM experiencias WHERE id IN (1,2,3);

-- ============================================
-- EXPERIENCIAS
-- ============================================

INSERT INTO experiencias (id, nombre, tipo, precio_base, precio_fijo, descripcion, activo)
VALUES
    (1, 'ESTÁNDAR', 'ESTANDAR', 15000.00, 1, 'Paquete básico con las comodidades esenciales para tu evento', 1),
    (2, 'PERSONALIZADA', 'PERSONALIZADA', 15000.00, 0, 'Armá tu propia experiencia seleccionando los extras que necesites', 1),
    (3, 'PROMOCIÓN - MES DICIEMBRE', 'PROMOCION', 20000.00, 1, 'Paquete especial del mes con servicios premium a precio promocional', 1);

-- ============================================
-- ITEMS DE EXPERIENCIA - ESTÁNDAR (ID=1)
-- ============================================

INSERT INTO items_experiencia (id, experiencia_id, nombre, incluido, costo_adicional, obligatorio)
VALUES
    (1, 1, 'Parrilla completa', 1, 0.00, 0),
    (2, 1, 'Mesa y sillas (8 personas)', 1, 0.00, 0),
    (3, 1, 'Baño completo', 1, 0.00, 0),
    (4, 1, 'Iluminación exterior', 1, 0.00, 0),
    (5, 1, 'Horno de barro', 0, 0.00, 0),
    (6, 1, 'Pileta climatizada', 0, 0.00, 0),
    (7, 1, 'Barra con heladera', 0, 0.00, 0),
    (8, 1, 'Fogón', 0, 0.00, 0),
    (9, 1, 'Sistema de música ambiente', 0, 0.00, 0),
    (10, 1, 'Quincho techado', 0, 0.00, 0);

-- ============================================
-- ITEMS DE EXPERIENCIA - PERSONALIZADA (ID=2)
-- ============================================

INSERT INTO items_experiencia (id, experiencia_id, nombre, incluido, costo_adicional, obligatorio)
VALUES
    (11, 2, 'Parrilla completa', 1, 0.00, 1),
    (12, 2, 'Mesa y sillas (8 personas)', 1, 0.00, 1),
    (13, 2, 'Baño completo', 1, 0.00, 1),
    (14, 2, 'Iluminación exterior', 1, 0.00, 1),
    (15, 2, 'Horno de barro', 0, 3000.00, 0),
    (16, 2, 'Pileta climatizada', 0, 5000.00, 0),
    (17, 2, 'Barra con heladera', 0, 2500.00, 0),
    (18, 2, 'Fogón', 0, 2000.00, 0),
    (19, 2, 'Sistema de música ambiente', 0, 3500.00, 0),
    (20, 2, 'Quincho techado', 0, 4000.00, 0);

-- ============================================
-- ITEMS DE EXPERIENCIA - PROMOCIÓN (ID=3)
-- ============================================

INSERT INTO items_experiencia (id, experiencia_id, nombre, incluido, costo_adicional, obligatorio)
VALUES
    (21, 3, 'Parrilla completa', 1, 0.00, 0),
    (22, 3, 'Mesa y sillas (8 personas)', 1, 0.00, 0),
    (23, 3, 'Baño completo', 1, 0.00, 0),
    (24, 3, 'Iluminación exterior', 1, 0.00, 0),
    (25, 3, 'Horno de barro', 1, 0.00, 0),
    (26, 3, 'Pileta climatizada', 1, 0.00, 0),
    (27, 3, 'Barra con heladera', 1, 0.00, 0),
    (28, 3, 'Fogón', 0, 0.00, 0),
    (29, 3, 'Sistema de música ambiente', 1, 0.00, 0),
    (30, 3, 'Quincho techado', 0, 0.00, 0);

-- ============================================
-- RESETEAR AUTO_INCREMENT
-- ============================================

ALTER TABLE experiencias AUTO_INCREMENT = 4;
ALTER TABLE items_experiencia AUTO_INCREMENT = 31;

-- ============================================
-- VERIFICACIÓN
-- ============================================

SELECT 'Experiencias insertadas:' as mensaje, COUNT(*) as total FROM experiencias;
SELECT 'Items insertados:' as mensaje, COUNT(*) as total FROM items_experiencia;
