package cat.itacademy.blackjackapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Elemento del ranking de jugadores.
 * Este DTO se deriva de la vista SQL (MySQL) y se expone en el endpoint /ranking.
 */
public record RankingItem(

        /**
         * Identificador único del jugador (UUID).
         */
        @Schema(
                description = "Unique identifier of the player",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID playerId,

        /**
         * Nombre del jugador.
         */
        @Schema(
                description = "Name of the player",
                example = "Alice"
        )
        String name,

        /**
         * Número de partidas ganadas.
         */
        @Schema(
                description = "Number of games won by the player",
                example = "15"
        )
        long wins,

        /**
         * Número de partidas perdidas.
         */
        @Schema(
                description = "Number of games lost by the player",
                example = "7"
        )
        long losses,

        /**
         * Número de veces que el jugador ha hecho Blackjack.
         */
        @Schema(
                description = "Number of Blackjacks achieved by the player",
                example = "3"
        )
        long blackjacks,

        /**
         * Ganancias totales acumuladas por el jugador.
         * En este proyecto las apuestas no están activas, pero se deja el campo para consistencia.
         */
        @Schema(
                description = "Total earnings of the player",
                example = "250.00"
        )
        BigDecimal earnings,

        /**
         * Porcentaje de victorias del jugador.
         * Se calcula como (wins / total games) * 100.
         */
        @Schema(
                description = "Win rate of the player (as percentage)",
                example = "68.2"
        )
        BigDecimal winRate
) {}
