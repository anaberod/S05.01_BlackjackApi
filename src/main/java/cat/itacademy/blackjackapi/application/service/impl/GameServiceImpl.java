package cat.itacademy.blackjackapi.application.service.impl;

import cat.itacademy.blackjackapi.application.dto.GameResponse;
import cat.itacademy.blackjackapi.application.mapper.GameDtoMapper;
import cat.itacademy.blackjackapi.application.service.GameService;
import cat.itacademy.blackjackapi.domain.model.GameAction;
import cat.itacademy.blackjackapi.domain.model.GameStatus;
import cat.itacademy.blackjackapi.domain.mongo.document.GameDocument;
import cat.itacademy.blackjackapi.domain.mongo.document.HandDocument;
import cat.itacademy.blackjackapi.domain.mongo.repository.GameDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameDocumentRepository gameDocumentRepository;
    private final GameDtoMapper gameDtoMapper;

    @Override
    public Mono<GameResponse> createGame(String playerName) {
        // Por ahora el playerName no se usa (no hay Player en MySQL aún)
        UUID playerId = UUID.randomUUID();
        UUID gameId   = UUID.randomUUID();
        Instant now   = Instant.now();

        // Mano inicial vacía (sin lógica de reparto todavía)
        HandDocument emptyHand = HandDocument.builder()
                .cards(List.of())
                .total(0)
                .blackjack(false)
                .busted(false)
                .build();

        GameDocument doc = GameDocument.builder()
                .gameId(gameId)
                .playerId(playerId)
                .status(GameStatus.IN_PROGRESS) // o NEW si prefieres
                .shoe(List.of())                // TODO: inicializar shoe barajado
                .playerHand(emptyHand)
                .dealerHand(emptyHand)
                .holeHidden(true)               // carta oculta del dealer
                .history(List.of())             // TODO: añadir movimientos
                .createdAt(now)
                .updatedAt(now)
                .build();

        return gameDocumentRepository
                .save(doc)
                .map(gameDtoMapper::toResponse);
    }

    @Override
    public Mono<GameResponse> getGame(UUID gameId) {
        return gameDocumentRepository
                .findByGameId(gameId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Game not found: " + gameId)))
                .map(gameDtoMapper::toResponse);
    }

    @Override
    public Mono<Void> deleteGame(UUID gameId) {
        return gameDocumentRepository.findByGameId(gameId)
                .flatMap(doc -> gameDocumentRepository.deleteById(doc.getId()));
        // Si quisieras error cuando no existe:
        // .switchIfEmpty(Mono.error(new IllegalArgumentException("Game not found: " + gameId)))
    }

    @Override
    public Mono<GameResponse> play(UUID gameId, GameAction action) {
        // TODO: implementar reglas reales (HIT, STAND, DEAL, DOUBLE)
        Instant now = Instant.now();

        return gameDocumentRepository.findByGameId(gameId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Game not found: " + gameId)))
                .flatMap(doc -> {
                    // Actualización mínima para que quede registrado algún cambio
                    doc.setUpdatedAt(now);
                    return gameDocumentRepository.save(doc);
                })
                .map(gameDtoMapper::toResponse);
    }
}
