-- ========================================
-- SCRIPT SQL: Sistema de Encuestas de Satisfacción
-- Proyecto: El Umbral - Quincho Reservas
-- ========================================

-- ----------------------------------------
-- 1. CREAR TABLA ENCUESTAS_SATISFACCION
-- ----------------------------------------

CREATE TABLE IF NOT EXISTS encuestas_satisfaccion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    reserva_id BIGINT NOT NULL,

    -- Preguntas principales (obligatorias, escala 1-5)
    satisfaccion_general INT NOT NULL CHECK (satisfaccion_general BETWEEN 1 AND 5),
    cumplio_expectativas INT NOT NULL CHECK (cumplio_expectativas BETWEEN 1 AND 5),
    recomendaria INT NOT NULL CHECK (recomendaria BETWEEN 1 AND 5),
    volveria INT NOT NULL CHECK (volveria BETWEEN 1 AND 5),

    -- Feedback textual (opcional)
    por_que_recomendaria TEXT,
    que_gusto TEXT,
    que_mejorar TEXT,
    que_agregar TEXT,

    -- Metadata
    fecha_respuesta DATETIME NOT NULL,

    -- Foreign keys
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (reserva_id) REFERENCES reservas(id) ON DELETE CASCADE,

    -- Índices
    INDEX idx_cliente (cliente_id),
    INDEX idx_reserva (reserva_id),
    INDEX idx_fecha_respuesta (fecha_respuesta DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------------------
-- 2. ALTERAR TABLA RESERVAS (SEGURO)
-- ----------------------------------------

-- Verificar si las columnas ya existen antes de agregarlas
-- Si ya existen, esto dará error pero es seguro ignorarlo

-- Agregar encuesta_token
ALTER TABLE reservas 
ADD COLUMN encuesta_token VARCHAR(255) UNIQUE;

-- Agregar token_encuesta_usado
ALTER TABLE reservas 
ADD COLUMN token_encuesta_usado BOOLEAN DEFAULT FALSE;

-- Crear índice para búsqueda por token
CREATE INDEX idx_encuesta_token ON reservas(encuesta_token);

-- ========================================
-- SCRIPT COMPLETADO
-- ========================================
-- Tablas creadas/modificadas:
-- 1. encuestas_satisfaccion (NUEVA - con IF NOT EXISTS es seguro)
-- 2. reservas (MODIFICADA - si columnas ya existen, ignorar error)
--
-- NOTA: Si las columnas ya existen, verás errores "Duplicate column name"
-- Esto es NORMAL y SEGURO - significa que ya están creadas.
--
-- Para revertir cambios:
-- DROP TABLE IF EXISTS encuestas_satisfaccion;
-- ALTER TABLE reservas DROP COLUMN encuesta_token;
-- ALTER TABLE reservas DROP COLUMN token_encuesta_usado;
-- DROP INDEX idx_encuesta_token ON reservas;
-- ========================================