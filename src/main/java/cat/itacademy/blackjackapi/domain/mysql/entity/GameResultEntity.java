package cat.itacademy.blackjackapi.domain.mysql.entity;

import cat.itacademy.blackjackapi.domain.model.ResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Resultado de una partida cerrada. Tabla: game_results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("game_results")
public class GameResultEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("player_id")
    private UUID playerId;

    @Column("result")
    private ResultType result;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
