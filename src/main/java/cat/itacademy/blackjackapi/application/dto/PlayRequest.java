package cat.itacademy.blackjackapi.application.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Petición para realizar una acción en la partida.
 * Acciones típicas: "DEAL", "HIT", "STAND", "DOUBLE".
 * Nota: En este proyecto no hay apuestas; 'bet' puede venir null y se ignora salvo que la lógica futura lo requiera.
 */
public record PlayRequest(
        @NotBlank(message = "action is required")
        String action,
        BigDecimal bet
) {}
