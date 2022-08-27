package info.nmrony.spring.tutorials.security_rbac.rest.dtos;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserView user;
}
