package id.ac.ui.cs.advprog.bookubeauth.services;

import id.ac.ui.cs.advprog.bookubeauth.models.User;
import id.ac.ui.cs.advprog.bookubeauth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(User.builder().email("user1@example.com").password("password1").build());
        expectedUsers.add(User.builder().email("user2@example.com").password("password2").build());

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.allUsers();

        verify(userRepository, times(1)).findAll();
        assertEquals(expectedUsers, actualUsers);
    }
}