package md.brainet.service.bank.controller.view;

public class NotAccessException extends RuntimeException {

    public NotAccessException() {
        super("Don't have enough rights to modify");
    }

    public NotAccessException(String message) {
        super(message);
    }
}
