package cat.itacademy.blackjackapi.domain.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Mano de cartas de un jugador o del dealer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HandDocument {

    private List<CardDocument> cards;
    private int total;
    private boolean blackjack;
    private boolean busted;
    private boolean standing;
}
