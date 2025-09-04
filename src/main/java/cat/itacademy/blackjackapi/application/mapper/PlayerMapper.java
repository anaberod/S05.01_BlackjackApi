package cat.itacademy.blackjackapi.application.mapper;

import cat.itacademy.blackjackapi.application.dto.PlayerView;
import cat.itacademy.blackjackapi.domain.mysql.entity.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface PlayerMapper {
    @Mapping(target = "id",        source = "id")
    @Mapping(target = "name",      source = "name")
    @Mapping(target = "balance",   source = "balance")
    @Mapping(target = "createdAt", source = "createdAt")
    PlayerView toView(PlayerEntity entity);
}
