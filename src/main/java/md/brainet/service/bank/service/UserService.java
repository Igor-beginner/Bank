package md.brainet.service.bank.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import md.brainet.service.bank.controller.view.payload.AdminPayloadOfUserCreation;
import md.brainet.service.bank.controller.view.payload.UpdateUserPayload;
import md.brainet.service.bank.entity.Role;
import md.brainet.service.bank.entity.User;
import md.brainet.service.bank.repository.UserRepository;
import md.brainet.service.bank.service.exception.UnknownUserLoginException;
import md.brainet.service.bank.service.exception.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void createDefaultUser() {
        saveNewUser("admin", "admin", Role.ADMIN);
        saveNewUser("user", "user", Role.USER);
        log.info("Test admin with name 'admin' has been created");
        log.info("Test user with name 'user' has been created");
    }

    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UnknownUserLoginException(String
                        .format("User with name %s does not exist", username)));
    }

    public User find(int id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UnknownUserLoginException(String
                        .format("User with id %s doesn't exist", id)));
    }

    public User find(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UnknownUserLoginException(String
                        .format("User with username %s doesn't exist", username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
           user = findByUsername(username);
        }catch (UnknownUserLoginException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                Collections.singleton(user.getRole()));
    }

    public List<User> findAll() {
        Iterator<User> iterator = userRepository.findAll().iterator();
        List<User> users = new ArrayList<>();
        iterator.forEachRemaining(users::add);
        return users;
    }

    public User saveNewUser(String username, String password) {
        return saveNewUser(username, password, Role.USER);
    }

    public User saveNewUser(String username, String password, Role role) {
        if (userRepository.existsByUsername(username)){
            throw new UserAlreadyExistException(String
                    .format("User with name %s already exist", username));
        }
        String passwordEncoded = passwordEncoder.encode(password);
        User newUser = User.builder()
                .username(username)
                .password(passwordEncoded)
                .role(role)
                .enabled(true)
                .build();
        return userRepository.save(newUser);
    }

    public User edit(UpdateUserPayload updateUserPayload, String oldUsername) {
        User user = find(oldUsername);
        user.setUsername(updateUserPayload.username());
        if(!Objects.equals(updateUserPayload.password(), "")) {
            user.setPassword(passwordEncoder.encode(updateUserPayload.password()));
        }
        return userRepository.save(user);
    }

    public User edit(AdminPayloadOfUserCreation payload, String oldUsername) {
        User user = find(oldUsername);
        user.setUsername(payload.username());
        user.setEnabled(payload.enabled());
        user.setRole(payload.role());
        if(!Objects.equals(payload.password(), "")) {
            user.setPassword(passwordEncoder.encode(payload.password()));
        }
        return userRepository.save(user);
    }
}
