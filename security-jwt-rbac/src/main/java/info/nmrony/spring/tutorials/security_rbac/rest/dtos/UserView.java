package info.nmrony.spring.tutorials.security_rbac.rest.dtos;

import java.util.HashSet;
import java.util.Set;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.Role;
import lombok.Data;

@Data
public class UserView {

    private Long id;

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles = new HashSet<>();

}
