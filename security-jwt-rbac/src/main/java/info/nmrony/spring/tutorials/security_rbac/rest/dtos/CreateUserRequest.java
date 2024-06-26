package info.nmrony.spring.tutorials.security_rbac.rest.dtos;

import java.util.Set;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.Role;
import info.nmrony.spring.tutorials.security_rbac.domain.validators.PasswordsEqualConstraint;
import info.nmrony.spring.tutorials.security_rbac.domain.validators.UsernameExistConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
