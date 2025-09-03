package cat.itacademy.blackjackapi.application.service.impl;

import cat.itacademy.blackjackapi.application.mapper.PlayerMapper;
import cat.itacademy.blackjackapi.application.service.PlayerService;
import cat.itacademy.blackjackapi.domain.mysql.entity.PlayerEntity;
import cat.itacademy.blackjackapi.domain.mysql.repository.PlayerRepository;
import cat.itacademy.blackjackapi.web.dto.PlayerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Implementación mínima del servicio de jugador.
 * Usa PlayerRepository cuando lo tengas definido. Si aún no,
 * puedes dejar este servicio para más adelante.
 */
@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public Mono<PlayerView> getPlayer(UUID playerId) {
        return playerRepository.findById(playerId)
                .map(playerMapper::toView);
    }

    @Override
    public Mono<PlayerView> renamePlayer(UUID playerId, String newName) {
        return playerRepository.findById(playerId)
                .flatMap(entity -> {
                    entity.setName(newName);
                    return playerRepository.save(entity);
                })
                .map(playerMapper::toView);
    }
}
