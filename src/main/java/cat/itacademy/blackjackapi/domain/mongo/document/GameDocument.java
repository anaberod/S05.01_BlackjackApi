package cat.itacademy.blackjackapi.domain.mongo.document;

import cat.itacademy.blackjackapi.domain.model.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Estado completo de una partida activa en Mongo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
public class GameDocument {

    @Id
    private String id; // Mongo genera su propio ObjectId, pero podemos usar gameId aparte

    @Field("game_id")
    private UUID gameId;

    @Field("player_id")
    private UUID playerId;

    private GameStatus status;

    // Shoe = mazo de cartas barajado para repartir
    private List<CardDocument> shoe;

    private HandDocument playerHand;
    private HandDocument dealerHand;

    private List<MoveDocument> history;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
