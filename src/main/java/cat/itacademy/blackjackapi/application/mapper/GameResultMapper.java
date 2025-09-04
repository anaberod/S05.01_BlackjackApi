package cat.itacademy.blackjackapi.application.mapper;

import cat.itacademy.blackjackapi.application.dto.RankingItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.UUID;

/** Mapper para ranking (vista SQL) -> DTO. Genera bean Spring y mantiene compatibilidad con fromFields(...). */
@Mapper(config = MapStructConfig.class)
public interface GameResultMapper {

    /** Proyección devuelta por el repositorio (ajusta getters si tu vista usa otros nombres). */
    interface RankingProjection {
        UUID getPlayerId();
        String getName();
        long getWins();
        long getLosses();
        long getBlackjacks();
        BigDecimal getTotalEarnings();
        BigDecimal getWinRate();
    }

    /* === MapStruct “puro” (abstract) -> genera implementación y bean Spring === */
    @Mapping(target = "playerId",   source = "playerId")
    @Mapping(target = "name",       source = "name")
    @Mapping(target = "wins",       source = "wins")
    @Mapping(target = "losses",     source = "losses")
    @Mapping(target = "blackjacks", source = "blackjacks")
    @Mapping(target = "earnings",   source = "totalEarnings")
    @Mapping(target = "winRate",    source = "winRate")
    RankingItem toRankingItem(RankingProjection p); // <-- SIN 'default'

    /* === Compatibilidad con tu servicio/tests actuales === */
    default RankingItem fromFields(UUID playerId,
                                   String name,
                                   int wins,
                                   int losses,
                                   int blackjacks,
                                   BigDecimal totalEarnings,
                                   BigDecimal winRate) {
        return new RankingItem(
                playerId, name,
                (long) wins, (long) losses, (long) blackjacks,
                safe(totalEarnings), safe(winRate)
        );
    }

    default RankingItem fromFields(UUID playerId,
                                   String name,
                                   long wins,
                                   long losses,
                                   long blackjacks,
                                   BigDecimal totalEarnings,
                                   BigDecimal winRate) {
        return new RankingItem(
                playerId, name,
                wins, losses, blackjacks,
                safe(totalEarnings), safe(winRate)
        );
    }

    private static BigDecimal safe(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}
