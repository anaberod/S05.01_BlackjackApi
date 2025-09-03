package cat.itacademy.blackjackapi.application.mapper;

import cat.itacademy.blackjackapi.domain.mongo.document.CardDocument;
import cat.itacademy.blackjackapi.domain.mongo.document.HandDocument;
import cat.itacademy.blackjackapi.domain.mongo.document.GameDocument;
import cat.itacademy.blackjackapi.domain.util.CardUtils;
import cat.itacademy.blackjackapi.web.dto.CardView;
import cat.itacademy.blackjackapi.web.dto.DealerView;
import cat.itacademy.blackjackapi.web.dto.GameResponse;
import cat.itacademy.blackjackapi.web.dto.HandView;
import cat.itacademy.blackjackapi.web.dto.PlayerView;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapea documentos Mongo (GameDocument, HandDocument, CardDocument) a DTOs de respuesta.
 */
@Mapper(componentModel = "spring")
public interface GameDtoMapper {

    /* =========================
       CardDocument -> CardView
       ========================= */
    default CardView toView(CardDocument doc) {
        if (doc == null) return null;

        // Rank/Suit son enums -> convertimos a String
        String rank = doc.getRank() == null ? null : doc.getRank().name();
        String suit = doc.getSuit() == null ? null : doc.getSuit().name();

        return new CardView(rank, suit, CardUtils.getValues(rank));
    }

    /* =========================
       HandDocument -> HandView
       ========================= */
    default HandView toView(HandDocument doc) {
        if (doc == null) return null;

        List<CardView> cards = (doc.getCards() == null)
                ? List.of()
                : doc.getCards().stream().map(this::toView).toList();

        return new HandView(cards, doc.getTotal(), doc.isBlackjack(), doc.isBusted());
    }

    /* =========================
       Dealer (Hand + hole) -> DealerView
       ========================= */
    default DealerView toDealerView(HandDocument dealerHand, boolean holeHidden) {
        if (dealerHand == null) {
            return new DealerView(List.of(), 0, holeHidden);
        }
        List<CardView> cards = (dealerHand.getCards() == null)
                ? List.of()
                : dealerHand.getCards().stream().map(this::toView).toList();

        return new DealerView(cards, dealerHand.getTotal(), holeHidden);
    }

    /* =========================
       GameDocument -> GameResponse
       ========================= */
    default GameResponse fromDocument(GameDocument doc, PlayerView player) {
        if (doc == null) return null;

        HandView playerHand = toView(doc.getPlayerHand());
        DealerView dealer = toDealerView(doc.getDealerHand(), doc.isHoleHidden());
        String status = (doc.getStatus() == null) ? null : doc.getStatus().toString();

        return new GameResponse(doc.getGameId(), player, playerHand, dealer, status);
    }
}
