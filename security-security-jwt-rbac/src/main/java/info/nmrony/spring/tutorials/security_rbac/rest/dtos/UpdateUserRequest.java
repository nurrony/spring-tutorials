package info.nmrony.spring.tutorials.security_rbac.rest.dtos;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Set<Role> roles;

}
