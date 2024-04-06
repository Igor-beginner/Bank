package md.brainet.service.bank.security;

import md.brainet.service.bank.controller.view.payload.NewUserPayload;
import md.brainet.service.bank.controller.view.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationTest {

    @Autowired
    UserController userController;

    @Test
    void givenUser_TryToSaveTwoTimes_ExpectUserAlreadyExistException() {
        NewUserPayload newUserPayload = new NewUserPayload("Igorik",
                "12345678");

//        userController.saveNewUser(newUserPayload);

//        assertThrows(UserAlreadyExistException.class, () -> userController.saveNewUser(newUserPayload));
    }
}
