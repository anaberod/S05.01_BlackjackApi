package cat.itacademy.blackjackapi.application.service;

import cat.itacademy.blackjackapi.application.dto.GameResponse;
import cat.itacademy.blackjackapi.application.mapper.GameDtoMapper;
import cat.itacademy.blackjackapi.application.service.impl.GameServiceImpl;
import cat.itacademy.blackjackapi.domain.model.GameAction;
import cat.itacademy.blackjackapi.domain.mongo.document.GameDocument;
import cat.itacademy.blackjackapi.domain.mongo.repository.GameDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    GameDocumentRepository repo;

    @Mock
    GameDtoMapper mapper;

    @InjectMocks
    GameServiceImpl service;

    @Test
    void createGame_returnsGameResponse() {
        UUID gameId = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();

        GameDocument doc = new GameDocument();
        doc.setGameId(gameId);
        doc.setPlayerId(playerId);

        var dealer = new GameResponse.HandView(List.of(), 0, false, false);
        var player = new GameResponse.HandView(List.of(), 0, false, false);

        var response = new GameResponse(
                gameId,
                playerId,
                "IN_PROGRESS",
                dealer,
                player,
                List.of(),
                Instant.now()
        );

        when(repo.save(any(GameDocument.class))).thenReturn(Mono.just(doc));
        when(mapper.toResponse(any(GameDocument.class))).thenReturn(response);

        Mono<GameResponse> result = service.createGame("Alice");

        StepVerifier.create(result)
                .expectNextMatches(r ->
                        r.gameId().equals(gameId) &&
                                r.playerId().equals(playerId) &&
                                "IN_PROGRESS".equals(r.status()))
                .verifyComplete();
    }

    @Test
    void play_deal_returnsSameGame() {
        UUID gameId = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();

        GameDocument doc = new GameDocument();
        doc.setGameId(gameId);
        doc.setPlayerId(playerId);

        var dealer = new GameResponse.HandView(List.of(), 0, false, false);
        var player = new GameResponse.HandView(List.of(), 0, false, false);

        var response = new GameResponse(
                gameId,
                playerId,
                "IN_PROGRESS",
                dealer,
                player,
                List.of(),
                Instant.now()
        );

        when(repo.findByGameId(any(UUID.class))).thenReturn(Mono.just(doc));
        // 💡 NECESARIO porque play() hace flatMap(repo::save)
        when(repo.save(any(GameDocument.class))).thenReturn(Mono.just(doc));
        when(mapper.toResponse(any(GameDocument.class))).thenReturn(response);

        Mono<GameResponse> result = service.play(gameId, GameAction.DEAL);

        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
    }
}
