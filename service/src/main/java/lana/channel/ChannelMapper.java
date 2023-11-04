package lana.channel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    @Mapping(target = "telegramId", source = "chatID")
    Channel mapToModel(ChannelCreateDto createDto);
}
