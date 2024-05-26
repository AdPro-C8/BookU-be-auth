package id.ac.ui.cs.advprog.bookubeauth.services;

import id.ac.ui.cs.advprog.bookubeauth.dtos.LoginUserDto;
import id.ac.ui.cs.advprog.bookubeauth.dtos.RegisterUserDto;
import id.ac.ui.cs.advprog.bookubeauth.models.User;
import id.ac.ui.cs.advprog.bookubeauth.enums.UserRole;
import id.ac.ui.cs.advprog.bookubeauth.repositories.UserRepository;
import id.ac.ui.cs.advprog.bookubeauth.services.AuthenticationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthenticationManager authenticationManager;

  @InjectMocks
  private AuthenticationService authenticationService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testSignup() {
    RegisterUserDto input = new RegisterUserDto("test@example.com", "password", "testuser");

    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

    User expectedUser = User.builder()
        .email(input.getEmail())
        .password(passwordEncoder.encode(input.getPassword()))
        .username(input.getUsername())
        .role(UserRole.CUSTOMER.toString())
        .build();

    when(userRepository.save(any(User.class))).thenReturn(expectedUser);

    User actualUser = authenticationService.signup(input);

    verify(userRepository, times(1)).save(any(User.class));
    assertEquals(expectedUser, actualUser);
  }

  @Test
  public void testAuthenticate() {
    LoginUserDto input = new LoginUserDto("test@example.com", "password");

    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

    User expectedUser = User.builder()
        .email(input.getEmail())
        .password(passwordEncoder.encode(input.getPassword()))
        .username("testuser")
        .role(UserRole.CUSTOMER.toString())
        .build();

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
    when(userRepository.findByEmail(input.getEmail())).thenReturn(Optional.of(expectedUser));

    User actualUser = authenticationService.authenticate(input);

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userRepository, times(1)).findByEmail(input.getEmail());
    assertEquals(expectedUser, actualUser);
  }
}

// package id.ac.ui.cs.advprog.bookubeauth.services;

// import id.ac.ui.cs.advprog.bookubeauth.dtos.LoginUserDto;
// import id.ac.ui.cs.advprog.bookubeauth.dtos.RegisterUserDto;
// import id.ac.ui.cs.advprog.bookubeauth.models.User;
// import id.ac.ui.cs.advprog.bookubeauth.enums.UserRole;
// import id.ac.ui.cs.advprog.bookubeauth.repositories.UserRepository;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class AuthenticationService {
//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final AuthenticationManager authenticationManager;

//     public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//         this.authenticationManager = authenticationManager;
//     }


//     public User signup(RegisterUserDto input) {
//         User user = User.builder()
//                 .email(input.getEmail())
//                 .password(passwordEncoder.encode(input.getPassword()))
//                 .username(input.getUsername())
//                 .role(UserRole.CUSTOMER.toString())
//                 .build();

//         return userRepository.save(user);
//     }

//     public User authenticate(LoginUserDto input) {
//         authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         input.getEmail(),
//                         input.getPassword()
//                 )
//         );

//         return userRepository.findByEmail(input.getEmail())
//                 .orElseThrow();
//     }
// }