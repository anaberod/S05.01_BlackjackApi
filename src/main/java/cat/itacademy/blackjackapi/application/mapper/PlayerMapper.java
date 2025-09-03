package cat.itacademy.blackjackapi.application.mapper;

import cat.itacademy.blackjackapi.domain.mysql.entity.PlayerEntity;
import cat.itacademy.blackjackapi.web.dto.PlayerView;
import org.mapstruct.Mapper;

import java.util.UUID;

/**
 * Mapea PlayerEntity -> PlayerView.
 */
@Mapper(componentModel = "spring")
public interface PlayerMapper {

    default PlayerView toView(PlayerEntity entity) {
        if (entity == null) return null;
        return new PlayerView(
                entity.getId(),
                entity.getName(),
                entity.getBalance(),
                entity.getCreatedAt()
        );
    }
}
