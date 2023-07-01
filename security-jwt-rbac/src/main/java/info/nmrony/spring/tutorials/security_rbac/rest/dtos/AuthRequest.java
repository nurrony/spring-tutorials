package info.nmrony.spring.tutorials.security_rbac.rest.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

}
