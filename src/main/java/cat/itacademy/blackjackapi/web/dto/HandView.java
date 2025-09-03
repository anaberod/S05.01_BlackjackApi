package cat.itacademy.blackjackapi.web.dto;

import java.util.List;

/**
 * Mano del jugador o del dealer.
 */
public record HandView(
        List<CardView> cards,
        int total,
        boolean blackjack,
        boolean busted
) {}
