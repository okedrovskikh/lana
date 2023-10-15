package lana.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToEntity(UserCreateDto createDto);
}
