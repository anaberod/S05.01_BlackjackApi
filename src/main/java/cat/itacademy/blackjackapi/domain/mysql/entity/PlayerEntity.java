package cat.itacademy.blackjackapi.domain.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Jugador en MySQL (R2DBC). Tabla: players
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("players")
public class PlayerEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("name")
    private String name;

    @Column("balance")
    @Builder.Default
    private BigDecimal balance = new BigDecimal("1000.00");

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
