package cat.itacademy.blackjackapi.application.service;

import cat.itacademy.blackjackapi.web.dto.RankingItem;
import reactor.core.publisher.Flux;

/**
 * Servicio para consultar el ranking de jugadores.
 */
public interface RankingService {

    /**
     * Obtiene el ranking de jugadores basado en sus resultados.
     * @return flujo reactivo de elementos del ranking
     */
    Flux<RankingItem> getRanking();
}
