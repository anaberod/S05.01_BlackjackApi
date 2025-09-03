package cat.itacademy.blackjackapi.application.service.impl;

import cat.itacademy.blackjackapi.application.mapper.GameDtoMapper;
import cat.itacademy.blackjackapi.application.service.GameService;
import cat.itacademy.blackjackapi.domain.model.GameAction;
import cat.itacademy.blackjackapi.domain.model.GameStatus;
import cat.itacademy.blackjackapi.domain.mongo.document.CardDocument;
import cat.itacademy.blackjackapi.domain.mongo.document.GameDocument;
import cat.itacademy.blackjackapi.domain.mongo.document.HandDocument;
import cat.itacademy.blackjackapi.domain.mongo.repository.GameDocumentRepository;
import cat.itacademy.blackjackapi.web.dto.GameResponse;
import cat.itacademy.blackjackapi.web.dto.PlayerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implementación mínima (stub) del servicio de juego.
 * - createGame: crea un GameDocument básico en Mongo y devuelve el estado inicial
 * - getGame: recupera y mapea a GameResponse
 * - deleteGame: elimina por gameId
 * - play: no aplica reglas todavía; solo devuelve el estado (TODO)
 */
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameDocumentRepository gameDocumentRepository;
    private final GameDtoMapper gameDtoMapper;

    @Override
    public Mono<GameResponse> createGame(String playerName) {
        // Stub: generamos player y partida "en memoria" (sin tocar MySQL todavía)
        UUID playerId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

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
                .status(GameStatus.IN_PROGRESS) // o el estado que prefieras
                .shoe(List.of())               // TODO: inicializar shoe barajado
                .playerHand(emptyHand)
                .dealerHand(emptyHand)
                .holeHidden(true)              // carta del dealer oculta al inicio
                .history(List.of())            // TODO: añadir MoveDocument en cada acción
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        // PlayerView simulado (hasta que conectes con MySQL/PlayerRepository)
        PlayerView playerView = new PlayerView(
                playerId,
                playerName,
                BigDecimal.valueOf(1000), // saldo inicial ficticio
                Instant.now()
        );

        return gameDocumentRepository.save(doc)
                .map(saved -> gameDtoMapper.fromDocument(saved, playerView));
    }

    @Override
    public Mono<GameResponse> getGame(UUID gameId) {
        // Como es un stub, el PlayerView no se recupera de MySQL aún.
        // Devolvemos un PlayerView genérico. Cuando conectes MySQL, cámbialo por una consulta real.
        PlayerView playerView = new PlayerView(
                null,            // se podría rellenar buscando por doc.playerId
                "Player",        // nombre placeholder
                BigDecimal.valueOf(1000),
                null
        );

        return gameDocumentRepository.findByGameId(gameId)
                .map(doc -> gameDtoMapper.fromDocument(doc, playerView));
    }

    @Override
    public Mono<Void> deleteGame(UUID gameId) {
        return gameDocumentRepository.findByGameId(gameId)
                .flatMap(doc -> gameDocumentRepository.deleteById(doc.getId()));
    }

    @Override
    public Mono<GameResponse> play(UUID gameId, GameAction action) {
        // TODO: implementar reglas (HIT/ STAND/ DEAL/ DOUBLE)
        // Por ahora: no cambia nada, solo devuelve el estado actual.
        return getGame(gameId);
    }
}
