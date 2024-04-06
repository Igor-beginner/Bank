package md.brainet.service.bank.controller.view;

import jakarta.validation.ValidationException;
import md.brainet.service.bank.service.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExceptionHandler {


    //TODO add modelAttribute method

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String invalidArgumentsPage(ValidationException e,
                                       Model model) {
        model.addAttribute("err", true);
        model.addAttribute("message", e.getMessage());
        return "registration";
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistException.class)
    public String userExistPage(UserAlreadyExistException e, Model model) {
        model.addAttribute("err", true);
        model.addAttribute("message", e.getMessage());
        return "registration";
    }
}