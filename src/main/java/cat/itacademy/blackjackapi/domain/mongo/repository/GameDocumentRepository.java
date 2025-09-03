package cat.itacademy.blackjackapi.domain.mongo.repository;

import cat.itacademy.blackjackapi.domain.mongo.document.GameDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface GameDocumentRepository extends ReactiveMongoRepository<GameDocument, String> {

    Mono<GameDocument> findByGameId(UUID gameId);

    Flux<GameDocument> findByPlayerIdOrderByCreatedAtDesc(UUID playerId);
}
