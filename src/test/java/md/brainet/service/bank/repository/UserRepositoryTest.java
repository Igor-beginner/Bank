package md.brainet.service.bank.repository;

import md.brainet.service.bank.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void givenNameOfExistingUser_MakeQuery_ExpectIsPresent() {
        String username = "Igor";
        createAndSaveUserByUsername(username);

        Optional<User> user = userRepository.findByUsername(username);

        assertTrue(user.isPresent());
    }

    @Test
    void makeQueryForNotExistingUser_ExpectIsNotPresent() {
        Optional<User> user = userRepository.findByUsername("dfasfa");

        assertFalse(user.isPresent());
    }

    void createAndSaveUserByUsername(String name) {
        User user = new User();
        user.setUsername(name);
        userRepository.save(user);
    }
}