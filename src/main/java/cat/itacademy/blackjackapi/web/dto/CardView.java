package cat.itacademy.blackjackapi.web.dto;

/**
 * Representación simple de una carta.
 * rank: "A", "2".. "10", "J", "Q", "K"
 * suit: "HEARTS", "DIAMONDS", "CLUBS", "SPADES" (o símbolos si lo prefieres)
 * values: posibles valores (p.ej. As = [1,11])
 */
public record CardView(
        String rank,
        String suit,
        int[] values
) {}
