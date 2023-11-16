package info.nurrony.tutorials.spring.overriderest.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record Person(Integer id, String fullName, String phoneNumber, String address, LocalDate createdAt) {

}
