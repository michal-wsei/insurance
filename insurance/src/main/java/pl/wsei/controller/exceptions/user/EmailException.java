package pl.wsei.controller.exceptions.user;

public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }
}
