package lana.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends CoreException {

    public NotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }
}
