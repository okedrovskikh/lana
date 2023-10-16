package lana.post;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post mapToEntity(PostCreateDto createDto);
}
