package id.ac.ui.cs.advprog.bookubeauth.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.bookubeauth.enums.UserRole;
import id.ac.ui.cs.advprog.bookubeauth.models.User;
import id.ac.ui.cs.advprog.bookubeauth.models.UserBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.annotation.Target;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserTest {
  
    private User user;
  
    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testGetId() {
        UUID id = UUID.randomUUID();
        user.setId(id);
        assertEquals(id, user.getId());
    }
  
    @Test
    public void testGetEmail() {
        user.setEmail("joshua@gmail.com");
        assertEquals("joshua@gmail.com", user.getEmail());
    }

    @Test
    public void testGetUsername() {
        user.setEmail("joshua");
        assertEquals("joshua", user.getUsername());
    }

    @Test
    public void testValidUsername() {
        User user = User.builder()
            .username("validUsername_123")
            .build();
        assertEquals("validUsername_123", user.getName());
    }

    @Test
    public void testEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                .username("")
                .build();
        });
    }
    
    @Test
    public void testInvalidUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
            .username("!@#$%^&*({}>?:")
            .build();
        });
    }

    @Test
    public void testEmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                .email("")
                .build();
        });
    }
    
    @Test
    public void testValidEmail() {
        User user = User.builder()
            .email("email@gmail.com")
            .build();
        assertEquals("email@gmail.com", user.getUsername());
    }

    @Test
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                .email("invalidEmail")
                .build();
        });
    }

    @Test
    public void testValidPassword() {
        User user = User.builder()
            .password("validPassword123")
            .build();
        assertEquals("validPassword123", user.getPassword());
    }

    @Test
    public void testShortPassword() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                .password("123")
                .build();
        });
    }

    @Test
    public void testValidRole() {
        User user = User.builder()
            .role("CUSTOMER")
            .build();

        assertEquals("CUSTOMER", user.getRole());
    }

    @Test
    public void testEmptyRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                .role("")
                .build();
        });
    }

    @Test
    public void testInvalidRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                .role("INVALID_ROLE")
                .build();
        });
    }

    @Test
    public void testIsEnabled() {
        assertTrue(user.isEnabled());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void testGetAuthorities() {
        assertEquals(List.of(), user.getAuthorities());
    }
}
