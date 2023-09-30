package lana.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoreException extends RuntimeException {
    private final HttpStatus code;

    protected CoreException(HttpStatus code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
