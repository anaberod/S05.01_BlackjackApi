package cat.itacademy.blackjackapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Vista de un jugador para exponer en API.
 */
public record PlayerView(

        @Schema(
                description = "Unique identifier of the player",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID id,

        @Schema(
                description = "Name of the player",
                example = "Alice"
        )
        String name,

        @Schema(
                description = "Current balance of the player",
                example = "1000.00"
        )
        BigDecimal balance,

        @Schema(
                description = "Creation timestamp (UTC)",
                example = "2025-09-04T09:15:30Z"
        )
        Instant createdAt
) {}
