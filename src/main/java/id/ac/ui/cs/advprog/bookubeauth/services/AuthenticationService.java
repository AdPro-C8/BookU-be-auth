package id.ac.ui.cs.advprog.bookubeauth.services;

import id.ac.ui.cs.advprog.bookubeauth.dtos.LoginUserDto;
import id.ac.ui.cs.advprog.bookubeauth.dtos.RegisterUserDto;
import id.ac.ui.cs.advprog.bookubeauth.models.User;
import id.ac.ui.cs.advprog.bookubeauth.enums.UserRole;
import id.ac.ui.cs.advprog.bookubeauth.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public User signup(RegisterUserDto input) {
        User user = User.builder()
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .username(input.getUsername())
                .role(UserRole.CUSTOMER.toString())
                .build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}