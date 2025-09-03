package cat.itacademy.blackjackapi.application.service;

import cat.itacademy.blackjackapi.web.dto.PlayerView;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Servicio de gestión de jugadores.
 */
public interface PlayerService {

    /**
     * Recupera un jugador por su ID.
     * @param playerId identificador del jugador
     * @return información pública del jugador
     */
    Mono<PlayerView> getPlayer(UUID playerId);

    /**
     * Renombra a un jugador.
     * @param playerId identificador del jugador
     * @param newName nuevo nombre
     * @return jugador actualizado
     */
    Mono<PlayerView> renamePlayer(UUID playerId, String newName);
}
