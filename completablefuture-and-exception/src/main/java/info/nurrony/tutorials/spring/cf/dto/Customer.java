package info.nurrony.tutorials.spring.cf.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Integer id;

    private String fullName;

    private String phoneNumber;

    private String address;

    private LocalDate createdAt;

}
