package md.brainet.service.bank.security;

import md.brainet.service.bank.repository.UserRepository;
import md.brainet.service.bank.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SignInTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void userNotExist_SendToSignIn_ExpectBadCredentials() throws Exception {
        String username = "unknown";

        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("password", "fasdfsadf"))
                .andExpect(forwardedUrl("/error"));
    }

    @Test
    void userExist_SendCorrectCredentials_ExpectStatusOk() throws Exception {
        String username = "gang_of_four";
        String password = "2132131";

        userService.saveNewUser(username, password);


        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(forwardedUrl("/accounts"));
    }

    @Test
    void userExist_SendInvalidPassword_ExpectInvalidCredentials() throws Exception {
        String username = "gang_of_four";
        String password = "2132131";

        userService.saveNewUser(username, password);


        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("password", password + "fdasf"))
                .andExpect(forwardedUrl("/error"));
    }
}
