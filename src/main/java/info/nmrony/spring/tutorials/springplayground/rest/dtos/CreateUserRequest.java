package info.nmrony.spring.tutorials.springplayground.rest.dtos;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import info.nmrony.spring.tutorials.springplayground.domain.entities.Role;
import lombok.Data;

@Data
public class CreateUserRequest {
  @NotBlank
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
  private String rePassword;

  private Set<Role> roles;

}
