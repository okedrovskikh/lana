package lana.exceptions;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExceptionMapper {
    ErrorResponse mapToResponse(Throwable th);
}
