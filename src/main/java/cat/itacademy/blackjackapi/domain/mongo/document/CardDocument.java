package cat.itacademy.blackjackapi.domain.mongo.document;

import cat.itacademy.blackjackapi.domain.model.Rank;
import cat.itacademy.blackjackapi.domain.model.Suit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una carta individual dentro de un HandDocument.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDocument {
    private Rank rank;
    private Suit suit;
}
