package info.nmrony.spring.tutorials.springplayground.rest.dtos;

import lombok.Data;

@Data
public class AuthResponse {
  private String token;
  UserView user;
}
