package info.nurrony.tutorials.spring.overriderest.repositories;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import info.nurrony.tutorials.spring.overriderest.configs.DataGenerator;
import info.nurrony.tutorials.spring.overriderest.dto.Page;
import info.nurrony.tutorials.spring.overriderest.dto.Person;
import info.nurrony.tutorials.spring.overriderest.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PersonRepository {
    private final DataGenerator dataGenerator;

    public Person findById(Integer id) {
        log.info("Getting Person by id {}", id);
        Utils.loadingSimulator(2);
        return dataGenerator.getPersons().get(id);
    }

    public Page<Person> findAll(Integer offset, Integer resultPerPage) {
        Utils.loadingSimulator(2);
        Integer limit = resultPerPage == 0 ? Page.RESULTS_PER_PAGE : resultPerPage;
        Map<Integer, Person> persons = dataGenerator.getPersons();
        List<Person> pagedPersons = persons
                .entrySet()
                .stream()
                .skip(offset)
                .limit(limit)
                .map(Entry::getValue)
                .toList();
        return new Page<Person>(offset, limit, persons.size(), pagedPersons);
    }
}
