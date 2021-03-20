package info.nmrony.spring.tutorials.springplayground.rest.dtos;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import info.nmrony.spring.tutorials.springplayground.domain.entities.Role;
import info.nmrony.spring.tutorials.springplayground.rest.exceptions.PasswordsEqualConstraint;
import info.nmrony.spring.tutorials.springplayground.rest.exceptions.UsernameExistConstraint;
import lombok.Data;

@Data
@PasswordsEqualConstraint(message = "Passwords are not matched")
public class CreateUserRequest {
    @NotBlank
    @UsernameExistConstraint(message = "username is already taken")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    private Set<Role> roles;

}
