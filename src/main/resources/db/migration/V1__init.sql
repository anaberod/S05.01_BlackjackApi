-- V1__init.sql  (MySQL 8 compatible sin IF NOT EXISTS en CREATE INDEX)

-- Tabla de jugadores
CREATE TABLE IF NOT EXISTS players (
    id         BINARY(16)    NOT NULL PRIMARY KEY,
    name       VARCHAR(100)  NOT NULL,
    balance    DECIMAL(15,2) NOT NULL DEFAULT 1000.00,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_players_name UNIQUE (name),
    INDEX idx_players_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de resultados de partidas
-- result: WIN / LOSS / PUSH / BLACKJACK
CREATE TABLE IF NOT EXISTS game_results (
    id         BINARY(16)    NOT NULL PRIMARY KEY,
    player_id  BINARY(16)    NOT NULL,
    result     ENUM('WIN','LOSS','PUSH','BLACKJACK') NOT NULL,
    bet        DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    delta      DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_game_results_player
      FOREIGN KEY (player_id) REFERENCES players(id)
      ON DELETE CASCADE
      ON UPDATE RESTRICT,

    INDEX idx_game_results_player_id (player_id),
    INDEX idx_game_results_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

