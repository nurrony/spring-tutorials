package info.nmrony.tutorials.spring.batch.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Customer {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String sex;

    private String jobTitle;

    private String email;

    private String phone;

    private Date dob;


}
