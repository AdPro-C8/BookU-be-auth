package id.ac.ui.cs.advprog.bookubeauth.configs;

import id.ac.ui.cs.advprog.bookubeauth.configs.JwtAuthenticationFilter;
import id.ac.ui.cs.advprog.bookubeauth.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtTest {

  @Test
  public void testJwtAuthenticationFilter() throws ServletException, IOException {
    JwtService jwtService = mock(JwtService.class);
    UserDetailsService userDetailsService = mock(UserDetailsService.class);
    HandlerExceptionResolver handlerExceptionResolver = mock(HandlerExceptionResolver.class);

    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService, handlerExceptionResolver);

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    // Test case 1: No Authorization header
    when(request.getHeader("Authorization")).thenReturn(null);

    assertDoesNotThrow(() -> filter.doFilterInternal(request, response, filterChain));
    verify(filterChain).doFilter(request, response);

    reset(filterChain);

    // Test case 2: Invalid Authorization header
    when(request.getHeader("Authorization")).thenReturn("InvalidToken");

    assertDoesNotThrow(() -> filter.doFilterInternal(request, response, filterChain));
    verify(filterChain).doFilter(request, response);

    reset(filterChain);
    
    // Test case 3: Valid Authorization header
    String validToken = "Bearer validToken";
    when(request.getHeader("Authorization")).thenReturn(validToken);
    when(jwtService.extractUsername("validToken")).thenReturn("user@example.com");

    UserDetails userDetails = new User("user@example.com", "password", new ArrayList<>());
    when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
    when(jwtService.isTokenValid("validToken", userDetails)).thenReturn(true);

    assertDoesNotThrow(() -> filter.doFilterInternal(request, response, filterChain));
    verify(filterChain).doFilter(request, response);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertEquals(userDetails, authentication.getPrincipal());
  }
}