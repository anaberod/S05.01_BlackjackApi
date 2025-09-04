package cat.itacademy.blackjackapi.application.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Petición para crear una nueva partida indicando el nombre del jugador.
 */
public record CreateGameRequest(
        @NotBlank(message = "playerName is required")
        String playerName
) {}
