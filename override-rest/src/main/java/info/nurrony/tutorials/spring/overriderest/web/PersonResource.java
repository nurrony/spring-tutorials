package info.nurrony.tutorials.spring.overriderest.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.nurrony.tutorials.spring.overriderest.core.SuccessResponse.WithResource;
import info.nurrony.tutorials.spring.overriderest.dto.Pageable;
import info.nurrony.tutorials.spring.overriderest.dto.Person;
import info.nurrony.tutorials.spring.overriderest.services.PersonService;
import info.nurrony.tutorials.spring.overriderest.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = { "/api/v1/person", "/api/v1/persons" })
public class PersonResource {
    final private PersonService personService;

    @GetMapping(value = { "list", "" })
    public ResponseEntity<?> listPerson(HttpServletRequest request,
            @RequestParam(defaultValue = "0", required = false) Integer offset,
            @RequestParam(defaultValue = "5", required = false) Integer limit) {
        log.info("uri = {} ", request.getRequestURI());

        Pageable pageable = new Pageable(limit, offset);
        return ResponseEntity.ok(
                ResponseUtils.buildPaginatedResponse(personService.list(pageable), "person fetched successfully", 200));
    }

    @GetMapping("{id}")
    public ResponseEntity<WithResource<Person>> getById(@PathVariable Integer id, HttpServletRequest request) {
        log.info("uri = {} ", request.getRequestURI());
        return ResponseEntity
                .ok(ResponseUtils.buildResourceResponse(personService.getById(id), "person fetched successfully", 200));
    }

    @GetMapping(value = "{id}", )
    public ResponseEntity<WithResource<Person>> getById(@PathVariable Integer id, HttpServletRequest request) {
        log.info("uri = {} ", request.getRequestURI());
        return ResponseEntity
                .ok(ResponseUtils.buildResourceResponse(personService.getById(id), "person fetched successfully", 200));
    }

}
