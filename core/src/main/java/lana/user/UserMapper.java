package lana.user;

import lana.models.dto.user.UserCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToEntity(UserCreateDto createDto);
}
