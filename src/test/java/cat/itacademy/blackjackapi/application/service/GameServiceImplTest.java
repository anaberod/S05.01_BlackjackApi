package cat.itacademy.blackjackapi.application.service;


import cat.itacademy.blackjackapi.application.mapper.GameDtoMapper;
import cat.itacademy.blackjackapi.application.service.impl.GameServiceImpl;
import cat.itacademy.blackjackapi.domain.model.GameAction;
import cat.itacademy.blackjackapi.domain.mongo.document.GameDocument;
import cat.itacademy.blackjackapi.domain.mongo.repository.GameDocumentRepository;
import cat.itacademy.blackjackapi.web.dto.GameResponse;
import cat.itacademy.blackjackapi.web.dto.PlayerView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GameServiceImplTest {

    private GameDocumentRepository repo;
    private GameDtoMapper mapper;
    private GameServiceImpl service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(GameDocumentRepository.class);
        mapper = Mockito.mock(GameDtoMapper.class);
        service = new GameServiceImpl(repo, mapper);
    }

    @Test
    void createGame_returnsGameResponse() {
        UUID playerId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        GameDocument doc = new GameDocument();
        doc.setGameId(gameId);
        doc.setPlayerId(playerId);

        PlayerView player = new PlayerView(playerId, "Alice", BigDecimal.valueOf(1000), Instant.now());
        GameResponse response = new GameResponse(gameId, player, null, null, "IN_PROGRESS");

        when(repo.save(any())).thenReturn(Mono.just(doc));
        when(mapper.fromDocument(any(), any())).thenReturn(response);

        Mono<GameResponse> result = service.createGame("Alice");

        StepVerifier.create(result)
                .expectNextMatches(r -> r.gameId().equals(gameId) && r.player().name().equals("Alice"))
                .verifyComplete();
    }

    @Test
    void play_deal_returnsSameGame() {
        UUID gameId = UUID.randomUUID();
        GameDocument doc = new GameDocument();
        doc.setGameId(gameId);

        GameResponse response = new GameResponse(gameId, null, null, null, "IN_PROGRESS");

        when(repo.findByGameId(any())).thenReturn(Mono.just(doc));
        when(mapper.fromDocument(any(), any())).thenReturn(response);

        Mono<GameResponse> result = service.play(gameId, GameAction.DEAL);

        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
    }
}

