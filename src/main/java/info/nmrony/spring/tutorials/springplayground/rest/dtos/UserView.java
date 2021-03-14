package info.nmrony.spring.tutorials.springplayground.rest.dtos;

import lombok.Data;

@Data
public class UserView {

    private Long id;

    private String username;
    private String email;
    private String firstName;
    private String lastName;

}