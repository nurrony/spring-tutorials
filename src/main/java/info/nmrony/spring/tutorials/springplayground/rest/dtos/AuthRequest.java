package info.nmrony.spring.tutorials.springplayground.rest.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthRequest {
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

}