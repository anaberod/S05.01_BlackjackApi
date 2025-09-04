package cat.itacademy.blackjackapi.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Vista de un jugador para exponer en API.
 */
public record PlayerView(
        UUID id,
        String name,
        BigDecimal balance,
        Instant createdAt
) {}
