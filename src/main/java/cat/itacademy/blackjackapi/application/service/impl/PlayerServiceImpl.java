package cat.itacademy.blackjackapi.application.service.impl;

import cat.itacademy.blackjackapi.application.dto.PlayerView;
import cat.itacademy.blackjackapi.application.mapper.PlayerMapper;
import cat.itacademy.blackjackapi.application.service.PlayerService;
import cat.itacademy.blackjackapi.domain.mysql.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public Mono<PlayerView> getPlayer(UUID playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Player not found: " + playerId)))
                .map(playerMapper::toView);
    }

    @Override
    public Mono<PlayerView> renamePlayer(UUID playerId, String newName) {
        if (newName == null || newName.isBlank()) {
            return Mono.error(new IllegalArgumentException("New name must not be blank"));
        }
        final String trimmed = newName.trim();

        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Player not found: " + playerId)))
                .flatMap(entity -> {
                    entity.setName(trimmed);
                    return playerRepository.save(entity);
                })
                .map(playerMapper::toView);
    }
}
