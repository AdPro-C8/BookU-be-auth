package id.ac.ui.cs.advprog.bookubeauth.configs;

import id.ac.ui.cs.advprog.bookubeauth.repositories.UserRepository;
import id.ac.ui.cs.advprog.bookubeauth.configs.ApplicationConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ApplicationConfigurationTest {

    @Mock
    private UserRepository userRepository;

    private ApplicationConfiguration applicationConfiguration;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        applicationConfiguration = new ApplicationConfiguration(userRepository);
    }

    @Test
    public void testUserDetailsService() {
        String username = "test@example.com";
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        UserDetailsService userDetailsService = applicationConfiguration.userDetailsService();

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }

    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = applicationConfiguration.passwordEncoder();

        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertEquals(true, passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = new AuthenticationConfiguration() {
            @Override
            public AuthenticationManager getAuthenticationManager() throws Exception {
                return null;
            }
        };

        AuthenticationManager authenticationManager = applicationConfiguration.authenticationManager(authenticationConfiguration);

        assertEquals(null, authenticationManager);
    }

    @Test
    public void testAuthenticationProvider() {
        UserDetailsService userDetailsService = applicationConfiguration.userDetailsService();
        BCryptPasswordEncoder passwordEncoder = applicationConfiguration.passwordEncoder();

        AuthenticationProvider authenticationProvider = applicationConfiguration.authenticationProvider();

        // Cast to DaoAuthenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider = (DaoAuthenticationProvider) authenticationProvider;

        assertNotNull(daoAuthenticationProvider);
    }
}