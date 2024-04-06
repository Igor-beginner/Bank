package md.brainet.service.bank.validator;

import md.brainet.service.bank.controller.view.payload.AdminPayloadOfUserCreation;
import md.brainet.service.bank.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EditedUserValidatorTest {

    @Autowired
    private EditedUserValidator validator;

    @Test
    void givenEmptyPassword_ExpectTrue() {
        AdminPayloadOfUserCreation user = of("dsfadf", "");

        assertTrue(isValid(user));
    }

    @Test
    void givenShortPassword_ExpectFalse() {
        AdminPayloadOfUserCreation user = of("dsfadf", "fs");

        assertFalse(isValid(user));
    }

    @Test
    void givenInvalidUsername_ExpectIsValidFalse() {
        AdminPayloadOfUserCreation user = of("", "");

        assertFalse(isValid(user));
    }

    @Test
    void givenInvalidUsernameAndEmptyPassword_ExpectIsValidTrue() {
        AdminPayloadOfUserCreation user = of("", "    ");

        assertFalse(isValid(user));
    }

    @Test
    void givenInvalidUsernameAndPassword_ExpectIsValidFalse() {
        AdminPayloadOfUserCreation user = of("dsaf", "1");

        assertFalse(isValid(user));
    }


    AdminPayloadOfUserCreation of(String username, String password) {
        return new AdminPayloadOfUserCreation(username, password, Role.USER, true);
    }

    boolean isValid(AdminPayloadOfUserCreation payload) {
        return validator.isValid(payload, null);
    }
}