package md.brainet.service.bank.service.exception;

public class UnknownAccountException extends RuntimeException{
    public UnknownAccountException(String message) {
        super(message);
    }
}
