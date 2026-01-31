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
-- 2. ALTERAR TABLA RESERVAS
-- ----------------------------------------

-- Agregar campos para token de encuesta
ALTER TABLE reservas
ADD COLUMN IF NOT EXISTS encuesta_token VARCHAR(255) UNIQUE,
ADD COLUMN IF NOT EXISTS token_encuesta_usado BOOLEAN DEFAULT FALSE;

-- Crear índice para búsqueda por token de encuesta
CREATE INDEX IF NOT EXISTS idx_encuesta_token ON reservas(encuesta_token);

-- ========================================
-- SCRIPT COMPLETADO
-- ========================================
-- Tablas creadas/modificadas:
-- 1. encuestas_satisfaccion (NUEVA)
-- 2. reservas (MODIFICADA: + encuesta_token, token_encuesta_usado)
--
-- Para revertir cambios:
-- DROP TABLE IF EXISTS encuestas_satisfaccion;
-- ALTER TABLE reservas DROP COLUMN encuesta_token, DROP COLUMN token_encuesta_usado;
-- ========================================