package cat.itacademy.blackjackapi.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Vista pública del jugador.
 */
public record PlayerView(
        UUID id,
        String name,
        BigDecimal balance,
        Instant createdAt
) {}
