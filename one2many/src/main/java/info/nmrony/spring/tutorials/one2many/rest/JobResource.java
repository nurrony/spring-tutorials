package info.nmrony.spring.tutorials.one2many.rest;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.one2many.domain.Job;
import info.nmrony.spring.tutorials.one2many.services.JobService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
public class JobResource {
    public final JobService jobService;

    @GetMapping
    public ResponseEntity<Set<Job>> listByCompany(@RequestHeader("company-id") Long companyId,
            @RequestHeader("user-id") Long userId) {
        return ResponseEntity.ok().body(Set.of());
    }

    @GetMapping("/list")
    public ResponseEntity<Set<Job>> listAll() {
        return ResponseEntity.ok().body(Set.of());
    }

    @PostMapping
    public ResponseEntity<Job> create(@RequestHeader("company-id") Long companyId,
            @RequestHeader("user-id") Long userId, @RequestBody Job paylod) {
        paylod.setOwnerId(userId);
        paylod.setCompanyId(companyId);
        return ResponseEntity.ok().body(jobService.create(paylod));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> update(@RequestHeader("company-id") Long companyId,
            @RequestHeader("user-id") Long userId, @PathVariable Long id, @RequestBody Job paylod) {
        return ResponseEntity.ok().body(jobService.update(id, companyId, paylod));
    }

}
