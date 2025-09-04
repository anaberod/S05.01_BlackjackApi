package cat.itacademy.blackjackapi.application.mapper;

import cat.itacademy.blackjackapi.application.dto.GameResponse;
import cat.itacademy.blackjackapi.domain.mongo.document.CardDocument;
import cat.itacademy.blackjackapi.domain.mongo.document.GameDocument;
import cat.itacademy.blackjackapi.domain.mongo.document.HandDocument;
import cat.itacademy.blackjackapi.domain.mongo.document.MoveDocument;
import org.mapstruct.Mapper;

import java.util.List;

/** Mapea documentos Mongo -> DTOs de salida (implementación manual). */
@Mapper(config = MapStructConfig.class)
public interface GameDtoMapper {

    /** GameDocument -> GameResponse */
    default GameResponse toResponse(GameDocument doc) {
        if (doc == null) return null;
        return new GameResponse(
                doc.getGameId(),
                doc.getPlayerId(),
                doc.getStatus() == null ? null : doc.getStatus().name(),
                toHandView(doc.getDealerHand()),
                toHandView(doc.getPlayerHand()),   // <-- SINGULAR
                toHistory(doc.getHistory()),
                doc.getUpdatedAt()
        );
    }

    /** HandDocument -> GameResponse.HandView */
    default GameResponse.HandView toHandView(HandDocument hand) {
        if (hand == null) {
            return new GameResponse.HandView(List.of(), 0, false, false);
        }
        return new GameResponse.HandView(
                toCards(hand.getCards()),
                hand.getTotal(),
                hand.isBlackjack(),
                hand.isBusted()
        );
    }

    /** Lista de MoveDocument -> lista de MoveView */
    default List<GameResponse.MoveView> toHistory(List<MoveDocument> moves) {
        return moves == null ? List.of()
                : moves.stream()
                .map(m -> new GameResponse.MoveView(
                        m.getTimestamp(),
                        m.getAction() == null ? null : m.getAction().name(),
                        m.getDetails()))
                .toList();
    }

    /** Lista de CardDocument -> lista de CardView (rank/suit como String) */
    default List<GameResponse.CardView> toCards(List<CardDocument> cards) {
        return cards == null ? List.of()
                : cards.stream()
                .map(c -> new GameResponse.CardView(
                        c.getRank() == null ? null : c.getRank().name(),
                        c.getSuit() == null ? null : c.getSuit().name()))
                .toList();
    }
}
