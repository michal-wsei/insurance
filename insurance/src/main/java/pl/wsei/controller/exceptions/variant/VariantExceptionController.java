package pl.wsei.controller.exceptions.variant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.wsei.controller.responses.ApiResponse;

@ControllerAdvice
public class VariantExceptionController {
    @ExceptionHandler(value = VariantException.class)
    public ResponseEntity<Object> usernameException(VariantException exception) {
        return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
