package lana.post;

import lana.models.dto.post.PostCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post mapToEntity(PostCreateDto createDto);
}
