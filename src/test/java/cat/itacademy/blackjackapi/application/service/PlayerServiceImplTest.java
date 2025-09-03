package cat.itacademy.blackjackapi.application.service;

import cat.itacademy.blackjackapi.application.mapper.PlayerMapper;
import cat.itacademy.blackjackapi.application.service.impl.PlayerServiceImpl;
import cat.itacademy.blackjackapi.domain.mysql.entity.PlayerEntity;
import cat.itacademy.blackjackapi.domain.mysql.repository.PlayerRepository;
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

class PlayerServiceImplTest {

    private PlayerRepository repo;
    private PlayerMapper mapper;
    private PlayerServiceImpl service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(PlayerRepository.class);
        mapper = Mockito.mock(PlayerMapper.class);
        service = new PlayerServiceImpl(repo, mapper);
    }

    @Test
    void getPlayer_returnsPlayerView() {
        UUID id = UUID.randomUUID();
        PlayerEntity entity = new PlayerEntity();
        entity.setId(id);
        entity.setName("Bob");

        PlayerView view = new PlayerView(id, "Bob", BigDecimal.valueOf(500), Instant.now());

        when(repo.findById(id)).thenReturn(Mono.just(entity));
        when(mapper.toView(entity)).thenReturn(view);

        StepVerifier.create(service.getPlayer(id))
                .expectNext(view)
                .verifyComplete();
    }

    @Test
    void renamePlayer_updatesName() {
        UUID id = UUID.randomUUID();
        PlayerEntity entity = new PlayerEntity();
        entity.setId(id);
        entity.setName("Old");

        PlayerEntity updated = new PlayerEntity();
        updated.setId(id);
        updated.setName("New");

        PlayerView view = new PlayerView(id, "New", BigDecimal.valueOf(500), Instant.now());

        when(repo.findById(id)).thenReturn(Mono.just(entity));
        when(repo.save(any())).thenReturn(Mono.just(updated));
        when(mapper.toView(updated)).thenReturn(view);

        StepVerifier.create(service.renamePlayer(id, "New"))
                .expectNextMatches(v -> v.name().equals("New"))
                .verifyComplete();
    }
}
