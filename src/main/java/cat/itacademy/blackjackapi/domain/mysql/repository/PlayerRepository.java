package cat.itacademy.blackjackapi.domain.mysql.repository;

import cat.itacademy.blackjackapi.domain.mysql.entity.PlayerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PlayerRepository extends ReactiveCrudRepository<PlayerEntity, UUID> {

    Mono<Boolean> existsByName(String name);

    Mono<PlayerEntity> findByName(String name);
}
