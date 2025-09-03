package cat.itacademy.blackjackapi.application.service;

import cat.itacademy.blackjackapi.domain.model.GameAction;
import cat.itacademy.blackjackapi.web.dto.GameResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Servicio principal para gestionar partidas de Blackjack.
 */
public interface GameService {

    /**
     * Crea una nueva partida para un jugador dado.
     * @param playerName nombre del jugador
     * @return estado inicial de la partida
     */
    Mono<GameResponse> createGame(String playerName);

    /**
     * Recupera el estado actual de la partida.
     * @param gameId identificador de la partida
     * @return estado actual de la partida
     */
    Mono<GameResponse> getGame(UUID gameId);

    /**
     * Elimina una partida.
     * @param gameId identificador de la partida
     * @return operación completada
     */
    Mono<Void> deleteGame(UUID gameId);

    /**
     * Ejecuta una acción sobre una partida (HIT, STAND, etc.).
     * @param gameId identificador de la partida
     * @param action acción a realizar
     * @return estado actualizado de la partida
     */
    Mono<GameResponse> play(UUID gameId, GameAction action);
}
