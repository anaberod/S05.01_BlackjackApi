package cat.itacademy.blackjackapi.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Elemento del ranking de jugadores.
 * Derivado de la vista SQL (MySQL).
 */
public record RankingItem(
        UUID playerId,
        String name,
        long wins,
        long losses,
        long blackjacks,
        BigDecimal earnings,
        BigDecimal winRate
) {}
