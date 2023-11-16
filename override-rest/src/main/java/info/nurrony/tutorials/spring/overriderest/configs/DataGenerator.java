package info.nurrony.tutorials.spring.overriderest.configs;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

import info.nurrony.tutorials.spring.overriderest.dto.Person;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Configuration
@RequiredArgsConstructor
public class DataGenerator {
    private Map<Integer, Person> persons = new HashMap<>();

    @Bean
    public InitializingBean initClients() {
        Faker faker = new Faker();
        return () -> IntStream.rangeClosed(1, 10)
                .forEach(id -> {
                    Person person = createPerson(id, faker);
                    persons.put(person.id(), person);
                });
    }

    private Person createPerson(int customerId, Faker faker) {
        return Person.builder()
                .id(customerId)
                .fullName(faker.name().fullName())
                .phoneNumber(faker.phoneNumber().cellPhone())
                .createdAt(LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 10)))))
                .build();
    }
}
