package md.brainet.service.bank.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityUrlStatusAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkStatus403OnRequestingToAuthorizeUrl() throws Exception {
        requestClientError("/some-url");
    }

    @Test
    void checkStatus200OnRequestingToLoginPage() throws Exception {
        requestOk("/login");
    }

    @Test
    void checkStatus200OnRequestingToRegistrationPage() throws Exception {
        requestOk("/registration");
    }

    @Test
    void checkStatus200OnRequestingToMainPage() throws Exception {
        requestOk("/");
    }

    private void requestOk(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    private void requestClientError(String url) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().is4xxClientError());
    }
}