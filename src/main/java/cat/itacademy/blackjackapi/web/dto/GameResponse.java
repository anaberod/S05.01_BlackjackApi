package cat.itacademy.blackjackapi.web.dto;

import java.util.UUID;

/**
 * Respuesta principal del estado de una partida.
 * status: por ahora String (p.ej. "IN_PROGRESS", "PLAYER_BUST", "DEALER_BUST", "PLAYER_WIN", "DEALER_WIN", "PUSH").
 * Si más adelante quieres, podemos convertirlo a un enum.
 */
public record GameResponse(
        UUID gameId,
        PlayerView player,
        HandView playerHand,
        DealerView dealer,
        String status
) {}
