package cat.itacademy.blackjackapi.application.service.impl;

import cat.itacademy.blackjackapi.application.mapper.GameResultMapper;
import cat.itacademy.blackjackapi.application.service.RankingService;
import cat.itacademy.blackjackapi.web.dto.RankingItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Implementación mínima del ranking.
 * De momento devuelve datos mock. Cuando tengas la vista SQL (v_ranking)
 * y el repositorio listo, cámbialo para consultar MySQL y mapear con GameResultMapper.
 */
@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final GameResultMapper gameResultMapper;

    @Override
    public Flux<RankingItem> getRanking() {
        // MOCK: 2 filas de ejemplo
        RankingItem a = gameResultMapper.fromFields(
                UUID.randomUUID(), "Alice",
                10, 5, 2,
                new BigDecimal("150.00"), new BigDecimal("0.6667")
        );
        RankingItem b = gameResultMapper.fromFields(
                UUID.randomUUID(), "Bob",
                7, 8, 1,
                new BigDecimal("-20.00"), new BigDecimal("0.4667")
        );
        return Flux.just(a, b);
    }
}
