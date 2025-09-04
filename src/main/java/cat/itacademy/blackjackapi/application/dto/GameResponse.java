package cat.itacademy.blackjackapi.application.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Estado visible de una partida de Blackjack para exponer por la API.
 * El campo {@code status} se serializa como String (p.ej. "NEW", "IN_PROGRESS", "FINISHED"...).
 */
public record GameResponse(
        UUID gameId,
        UUID playerId,
        String status,              // "NEW","IN_PROGRESS","PLAYER_BUST","DEALER_BUST","PLAYER_BLACKJACK","FINISHED"
        HandView dealerHand,
        HandView playerHand,
        List<MoveView> history,
        Instant updatedAt
) {

    /** Mano (dealer o jugador). */
    public record HandView(
            List<CardView> cards,
            int total,
            boolean blackjack,
            boolean busted
    ) {}

    /** Carta de la baraja. */
    public record CardView(
            String rank,  // "A","2","3",...,"K"
            String suit   // "HEARTS","DIAMONDS","CLUBS","SPADES"
    ) {}

    /** Movimiento realizado en la partida. */
    public record MoveView(
            Instant timestamp,
            String action,  // "DEAL","HIT","STAND","DOUBLE",...
            String details
    ) {}

    /** Utilidad: mano vacía (sin cartas, total 0). */
    public static HandView emptyHand() {
        return new HandView(List.of(), 0, false, false);
    }
}
