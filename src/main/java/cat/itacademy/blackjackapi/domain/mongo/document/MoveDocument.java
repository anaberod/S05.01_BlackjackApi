package cat.itacademy.blackjackapi.domain.mongo.document;

import cat.itacademy.blackjackapi.domain.model.MoveAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Movimiento registrado en la historia de la partida.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveDocument {
    private Instant timestamp;
    private MoveAction action;
    private String details; // ej: "Player hit and got 7 of hearts"
}
