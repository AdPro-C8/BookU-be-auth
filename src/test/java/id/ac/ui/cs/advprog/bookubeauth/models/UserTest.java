package id.ac.ui.cs.advprog.bookubeauth.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {
  
    private User user;
  
    @BeforeEach
    public void setUp() {
        user = new User();
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
}
