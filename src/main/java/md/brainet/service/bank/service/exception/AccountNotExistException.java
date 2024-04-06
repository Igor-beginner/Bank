package md.brainet.service.bank.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotExistException extends RuntimeException {
    public AccountNotExistException(String message) {
        super(message);
    }
}
