package cat.itacademy.blackjackapi.domain.mysql.repository;

import cat.itacademy.blackjackapi.domain.mysql.entity.GameResultEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface GameResultRepository extends ReactiveCrudRepository<GameResultEntity, UUID> {

    Flux<GameResultEntity> findByPlayerIdOrderByCreatedAtDesc(UUID playerId);

    Flux<GameResultEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
