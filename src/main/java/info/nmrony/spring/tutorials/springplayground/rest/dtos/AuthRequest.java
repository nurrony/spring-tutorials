package info.nmrony.spring.tutorials.springplayground.rest.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import info.nmrony.spring.tutorials.springplayground.rest.exceptions.UsernameExistConstraint;
import lombok.Data;

@Data
public class AuthRequest {
    @NotEmpty
    @UsernameExistConstraint(message = "Username is not available")
    private String username;

    @NotNull
    @NotEmpty
    private String password;

}
