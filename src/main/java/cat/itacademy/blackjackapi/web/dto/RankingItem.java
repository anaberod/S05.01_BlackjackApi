package cat.itacademy.blackjackapi.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Elemento del ranking de jugadores.
 * Se alimenta de la vista SQL v_ranking (MySQL).
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
