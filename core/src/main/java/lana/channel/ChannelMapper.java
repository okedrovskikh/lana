package lana.channel;

import lana.models.dto.channel.ChannelCreateDto;
import lana.models.dto.channel.ChannelUpdateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    Channel mapToModel(ChannelCreateDto createDto);

    Channel mapToModel(ChannelUpdateDto updateDto);
}
