package lana.advice;

import lana.exceptions.CoreException;
import lana.exceptions.ExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@RestControllerAdvice
@RequiredArgsConstructor
public class ApiControllersAdvice {
    private final ExceptionMapper mapper;

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<?> handleCoreException(CoreException ex) {
        return ResponseEntity.status(ex.getCode()).body(mapper.mapToResponse(ex));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleThrowable(Throwable th) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(mapper.mapToResponse(th));
    }
}
