package md.brainet.service.bank.service.exception;

public class UnknownUserLoginException extends RuntimeException{
    public UnknownUserLoginException(String message) {
        super(message);
    }
}
