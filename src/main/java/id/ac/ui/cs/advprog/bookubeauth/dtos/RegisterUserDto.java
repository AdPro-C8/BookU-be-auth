package id.ac.ui.cs.advprog.bookubeauth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserDto {
    private String email;

    private String password;

    private String username;
}