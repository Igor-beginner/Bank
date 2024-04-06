package md.brainet.service.bank.controller.rest;

import md.brainet.service.bank.controller.view.payload.NewUserPayload;
import md.brainet.service.bank.controller.view.UserController;
import md.brainet.service.bank.entity.User;
import md.brainet.service.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    LocalValidatorFactoryBean validator;

    @Autowired
    UserController userController;

    @Test
    void saveUserWithValidateData() {
        when(userService.saveNewUser(any(String.class), any(String.class)))
                .thenAnswer(invocationOnMock ->
                        User.builder()
                            .id(10)
                            .username(invocationOnMock.getArgument(0))
                            .password(invocationOnMock.getArgument(1))
                            .build());

        NewUserPayload payload = new NewUserPayload("Igor-beginner", "1234567");

        //ResponseEntity<UserResponse> response = userController.saveNewUser(payload);

        //assertNotNull(response.getBody());
        //assertEquals(payload.username(), response.getBody().username());
    }

    @Test
    void saveUserWithInValidateData() {
        when(userService.saveNewUser(any(String.class), any(String.class)))
                .thenAnswer(invocationOnMock -> {
                    User user = User.builder()
                            .id(10)
                            .username(invocationOnMock.getArgument(0))
                            .password(invocationOnMock.getArgument(1))
                            .build();
                    validator.getValidator().validate(new NewUserPayload(user.getUsername(),
                            user.getPassword()), NewUserPayload.class);
                    return user;
                });

        NewUserPayload payload = new NewUserPayload("Ig", "1234567");

//        assertThrows(ValidationException.class, () -> userController.saveNewUser(payload));
    }
}