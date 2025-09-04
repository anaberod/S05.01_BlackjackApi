package cat.itacademy.blackjackapi.application.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Petición para renombrar un jugador.
 */
public record PlayerRenameRequest(
        @NotBlank(message = "name is required")
        String name
) {}
