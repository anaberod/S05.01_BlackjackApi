package cat.itacademy.blackjackapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Estado visible de una partida de Blackjack para exponer por la API.
 * El campo {@code status} se serializa como String (p.ej. "NEW", "IN_PROGRESS", "FINISHED"...).
 * DTO exclusivamente de salida (response).
 */
public record GameResponse(

        /** Identificador único de la partida. */
        @Schema(description = "Unique identifier of the game",
                example = "123e4567-e89b-12d3-a456-426614174000")
        UUID gameId,

        /** Identificador del jugador al que pertenece la partida. */
        @Schema(description = "Identifier of the player owning the game",
                example = "550e8400-e29b-41d4-a716-446655440000")
        UUID playerId,

        /**
         * Estado actual de la partida.
         * Mantienes String por simplicidad; si más adelante introduces un enum, puedes mapearlo a String igualmente.
         */
        @Schema(description = "Current game status",
                example = "IN_PROGRESS",
                allowableValues = {
                        "NEW","IN_PROGRESS","PLAYER_BUST","DEALER_BUST",
                        "PLAYER_BLACKJACK","FINISHED","PUSH","DEALER_WINS","PLAYER_WINS"
                })
        String status,              // "NEW","IN_PROGRESS","PLAYER_BUST","DEALER_BUST","PLAYER_BLACKJACK","FINISHED",...

        /** Mano del dealer. */
        @Schema(description = "Dealer's hand view")
        HandView dealerHand,

        /** Mano del jugador. */
        @Schema(description = "Player's hand view")
        HandView playerHand,

        /** Historial de movimientos realizados en la partida. */
        @Schema(description = "Chronological list of moves performed during the game")
        List<MoveView> history,

        /** Marca temporal de la última actualización (UTC). */
        @Schema(description = "Last update timestamp (UTC)",
                example = "2025-09-04T09:20:30Z")
        Instant updatedAt
) {

    /**
     * Mano (dealer o jugador).
     * NOTA: declarada como 'public static record' para evitar problemas con springdoc en records anidados.
     */
    @Schema(description = "Hand view (dealer or player)")
    public static record HandView(

            /** Cartas de la mano en el orden en que se han repartido. */
            @Schema(description = "Cards in the hand")
            List<CardView> cards,

            /** Total de puntos de la mano (ya evaluado con As como 1 u 11 según convenga). */
            @Schema(description = "Computed total value of the hand", example = "20")
            int total,

            /** Indica si la mano es Blackjack (A + 10/J/Q/K en las dos primeras cartas). */
            @Schema(description = "Whether the hand is a Blackjack", example = "false")
            boolean blackjack,

            /** Indica si la mano se ha pasado de 21. */
            @Schema(description = "Whether the hand is busted (over 21)", example = "false")
            boolean busted
    ) {}

    /**
     * Carta de la baraja.
     * NOTA: declarada como 'public static record' para compatibilidad con springdoc.
     */
    @Schema(description = "Single playing card")
    public static record CardView(

            /** Rango: "A","2"...,"10","J","Q","K". */
            @Schema(description = "Card rank", example = "A")
            String rank,

            /** Palo: "HEARTS","DIAMONDS","CLUBS","SPADES". */
            @Schema(description = "Card suit", example = "HEARTS")
            String suit
    ) {}

    /**
     * Movimiento realizado en la partida.
     * NOTA: declarada como 'public static record' para compatibilidad con springdoc.
     */
    @Schema(description = "Move performed in the game")
    public static record MoveView(

            /** Instante en que se realizó el movimiento. */
            @Schema(description = "Timestamp of the move (UTC)",
                    example = "2025-09-04T09:18:05Z")
            Instant timestamp,

            /**
             * Acción realizada: "DEAL","HIT","STAND","DOUBLE",...
             * Mantienes String para flexibilidad; en el futuro puedes migrarlo a enum si lo prefieres.
             */
            @Schema(description = "Action performed",
                    example = "HIT",
                    allowableValues = {"DEAL","HIT","STAND","DOUBLE","SPLIT"})
            String action,  // "DEAL","HIT","STAND","DOUBLE",...

            /** Detalles opcionales (p.ej., "Player hits and receives 10 of CLUBS"). */
            @Schema(description = "Optional details about the move",
                    example = "Player hits and receives K of SPADES")
            String details
    ) {}

    /**
     * Utilidad: mano vacía (sin cartas, total 0).
     * Útil para inicializar el estado cuando aún no se han repartido cartas.
     * Nota: List.of() crea una lista inmutable; perfecto para este DTO.
     */
    public static HandView emptyHand() {
        return new HandView(List.of(), 0, false, false);
    }
}
