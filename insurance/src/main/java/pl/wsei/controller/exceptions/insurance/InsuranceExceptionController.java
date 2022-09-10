package pl.wsei.controller.exceptions.insurance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.wsei.controller.responses.ApiResponse;

@ControllerAdvice
public class InsuranceExceptionController {
    @ExceptionHandler(value = InsuranceException.class)
    public ResponseEntity<Object> usernameException(InsuranceException exception) {
        return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
