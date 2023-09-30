package lana.channel;

import lana.models.dto.ChannelCreateDto;
import lana.models.dto.ChannelUpdateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    Channel mapToModel(ChannelCreateDto createDto);

    Channel mapToModel(ChannelUpdateDto updateDto);
}
