package id.ac.ui.cs.advprog.bookubeauth.repositories;

import id.ac.ui.cs.advprog.bookubeauth.models.User;
import id.ac.ui.cs.advprog.bookubeauth.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = true)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User mockUser;

    private User setupUser(String email, UserRole role) {
        return User.builder()
                .username("user12345")
                .email(email)
                .password("password")
                .role(role.toString())
                .build();
    }

    @BeforeEach
    void setUp() {
        mockUser = setupUser("myemail@email.com", UserRole.CUSTOMER);
        userRepository.save(mockUser);
        userRepository.save(setupUser("user2@email.com", UserRole.ADMIN));
    }

    @Test
    void findUserByEmail() {
        Optional<User> user = userRepository.findByEmail("myemail@email.com");
        if (user.isPresent()) {
            assertEquals(mockUser.getEmail(), user.get().getEmail());
        } else {
            fail("User not found");
        }
    }

    @Test
    void findUserByEmailNotFound() {
        Optional<User> user = userRepository.findByEmail("no@email.com");
        assertFalse(user.isPresent());
    }

    @Test
    void createUser() {
        String testEmail = "test@test.com";
        User user = setupUser(testEmail, UserRole.CUSTOMER);
        userRepository.save(user);
        Optional<User> savedUser = userRepository.findByEmail(testEmail);
        if (savedUser.isPresent()) {
            assertEquals(user.getEmail(), savedUser.get().getEmail());
        } else {
            fail("User not found");
        }
    }

    @Test
    void deleteUser() {
        String testEmail = "test@test.com";
        User user = setupUser(testEmail, UserRole.CUSTOMER);
        userRepository.save(user);
        Optional<User> savedUser = userRepository.findByEmail(testEmail);
        if (savedUser.isPresent()) {
            userRepository.delete(savedUser.get());
            Optional<User> deletedUser = userRepository.findByEmail(testEmail);
            assertFalse(deletedUser.isPresent());
        } else {
            fail("User not found");
        }
    }
}