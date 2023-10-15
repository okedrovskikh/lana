package lana.channel;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    Channel mapToModel(ChannelCreateDto createDto);
}
