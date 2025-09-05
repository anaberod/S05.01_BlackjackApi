package cat.itacademy.blackjackapi.web.controller;

import cat.itacademy.blackjackapi.application.dto.CreateGameRequest;
import cat.itacademy.blackjackapi.application.dto.GameResponse;
import cat.itacademy.blackjackapi.application.dto.PlayRequest;
import cat.itacademy.blackjackapi.application.service.GameService;
import cat.itacademy.blackjackapi.domain.model.GameAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/game")
@Tag(name = "Game", description = "Game endpoints")
public class GameController {

    private final GameService gameService;

    // Inyección del servicio por constructor (buena práctica en Spring)
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Crea una nueva partida para un jugador.
     * - Request: JSON con { "playerName": "Alice" }
     * - Response: estado inicial de la partida (201 Created)
     */
    @Operation(summary = "Create a new game")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameResponse> createGame(@Valid @RequestBody CreateGameRequest request) {
        return gameService.createGame(request.playerName());
    }

    /**
     * Recupera el estado actual de una partida por su ID.
     * - PathVariable: UUID del juego
     * - Response: estado actual (200 OK)
     */
    @Operation(summary = "Get a game by ID")
    @GetMapping("/{id}")
    public Mono<GameResponse> getGame(@PathVariable("id") UUID gameId) {
        return gameService.getGame(gameId);
    }

    /**
     * Ejecuta una acción en la partida (ej: DEAL, HIT, STAND, DOUBLE).
     * - PathVariable: UUID del juego
     * - RequestBody: { "action": "HIT", "bet": null }
     * - Convierte el String del request a enum GameAction.
     * - Response: estado actualizado de la partida (200 OK).
     */
    @Operation(summary = "Play an action in a game")
    @PostMapping("/{id}/play")
    public Mono<GameResponse> play(@PathVariable("id") UUID gameId,
                                   @Valid @RequestBody PlayRequest request) {
        final GameAction action;

        try {
            // Convertimos la acción del request a MAYÚSCULAS y buscamos el enum correspondiente.
            action = GameAction.valueOf(request.action().trim().toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            // Si la acción no existe en el enum, devolvemos un 400 Bad Request con mensaje claro.
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid action: " + request.action()
                            + " (allowed: " + String.join(", ",
                            java.util.Arrays.stream(GameAction.values()).map(Enum::name).toList()
                    ) + ")"
            );
        }

        // Nota: en este proyecto no usamos 'bet'; aunque venga en el request, se ignora.
        return gameService.play(gameId, action);
    }

    /**
     * Elimina/cierra una partida.
     * - PathVariable: UUID del juego
     * - Response: 204 No Content (sin cuerpo).
     */
    @Operation(summary = "Delete a game by ID")
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGame(@PathVariable("id") UUID gameId) {
        return gameService.deleteGame(gameId);
    }
}
