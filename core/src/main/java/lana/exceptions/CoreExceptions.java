package lana.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CoreExceptions {

    private CoreExceptions() {
        throw new IllegalStateException("Cannot create this class");
    }

    @Getter
    public static sealed class CoreException extends RuntimeException {
        private final HttpStatus code;

        protected CoreException(HttpStatus code, String message, Throwable cause) {
           super(message, cause);
           this.code = code;
        }
    }

    public static final class NotFoundException extends CoreException {

        public NotFoundException(String message, Throwable cause) {
            super(NOT_FOUND, message, cause);
        }
    }

}
