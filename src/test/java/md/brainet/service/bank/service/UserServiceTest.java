package md.brainet.service.bank.service;

import md.brainet.service.bank.entity.User;
import md.brainet.service.bank.repository.UserRepository;
import md.brainet.service.bank.service.exception.UnknownUserLoginException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void givenNameOfExistingUser_MakeQuery_NotExpectException() {
        String username = "Igor";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(User.builder()
                                .username(username).build()));

        userService.findByUsername(username);
    }

    @Test
    void givenNameOfExistingUser_MakeQuery_ExpectException() {
        String username = "Igor";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        assertThrows(UnknownUserLoginException.class,
                () -> userService.findByUsername(username));
    }
}