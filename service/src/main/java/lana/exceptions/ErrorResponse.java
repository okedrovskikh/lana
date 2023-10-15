package lana.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private String message;
    private List<ErrorResponse> innerErrors;
}
