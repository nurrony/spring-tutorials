package info.nurrony.tutorials.spring.overriderest.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import info.nurrony.tutorials.spring.overriderest.dto.Page;
import info.nurrony.tutorials.spring.overriderest.dto.Pageable;
import info.nurrony.tutorials.spring.overriderest.dto.Person;
import info.nurrony.tutorials.spring.overriderest.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public record PersonService(PersonRepository personRepository) {

    public Person getById(Integer id) {
        log.info("Getting customer by id {} using exceptionally.", id);
        return personRepository.findById(id);
    }

    public Page<Person> list(Pageable pageable) {
        return personRepository.findAll(pageable.offset(), Optional.ofNullable(pageable.limit()).orElse(0));
    }
}
