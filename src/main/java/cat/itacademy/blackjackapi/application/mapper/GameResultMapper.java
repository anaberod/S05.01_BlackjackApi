package cat.itacademy.blackjackapi.application.mapper;

import cat.itacademy.blackjackapi.web.dto.RankingItem;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Mapea resultados de partidas (vista SQL v_ranking o proyecciones) a RankingItem DTO.
 */
@Mapper(componentModel = "spring")
public interface GameResultMapper {

    /**
     * Proyección genérica que puedes usar en el repositorio MySQL
     * (Spring Data la rellenará a partir de la vista v_ranking).
     */
    interface RankingProjection {
        UUID getPlayerId();
        String getName();
        long getWins();
        long getLosses();
        long getBlackjacks();
        BigDecimal getEarnings();
        BigDecimal getWinRate();
    }

    /**
     * Convierte una proyección en DTO RankingItem.
     */
    default RankingItem fromProjection(RankingProjection p) {
        if (p == null) return null;
        return new RankingItem(
                p.getPlayerId(),
                p.getName(),
                p.getWins(),
                p.getLosses(),
                p.getBlackjacks(),
                p.getEarnings(),
                p.getWinRate()
        );
    }

    /**
     * Método alternativo para construir manualmente RankingItem (útil en tests).
     */
    default RankingItem fromFields(UUID playerId,
                                   String name,
                                   long wins,
                                   long losses,
                                   long blackjacks,
                                   BigDecimal earnings,
                                   BigDecimal winRate) {
        return new RankingItem(playerId, name, wins, losses, blackjacks, earnings, winRate);
    }
}
