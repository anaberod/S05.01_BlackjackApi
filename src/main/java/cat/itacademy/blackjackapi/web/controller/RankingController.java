package cat.itacademy.blackjackapi.web.controller;

import cat.itacademy.blackjackapi.application.dto.RankingItem;
import cat.itacademy.blackjackapi.application.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Exposición del ranking de jugadores.
 * GET /ranking -> 200 OK con un Flux<RankingItem>
 */
@RestController
@RequestMapping("/ranking")
@Tag(name = "Ranking", description = "Ranking endpoints")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @Operation(summary = "Get players ranking")
    @GetMapping
    public Flux<RankingItem> getRanking() {
        // Llama al servicio y devuelve el flujo reactivo con el ranking
        return rankingService.getRanking();
    }
}
