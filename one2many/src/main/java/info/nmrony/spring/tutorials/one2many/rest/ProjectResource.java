package info.nmrony.spring.tutorials.one2many.rest;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.one2many.domain.Project;
import info.nmrony.spring.tutorials.one2many.services.ProjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectResource {
    public final ProjectService projectService;

    @GetMapping
    public ResponseEntity<Set<Project>> listByCompany(@RequestHeader("company-id") Long companyId,
            @RequestHeader("user-id") Long userId) {
        var projects = projectService.listByCompany(companyId);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/list")
    public ResponseEntity<Set<Project>> listAll() {
        return ResponseEntity.ok().body(Set.of());
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestHeader("company-id") Long companyId,
            @RequestHeader("user-id") Long userId, @RequestBody Project paylod) {
        paylod.setOwnerId(userId);
        return ResponseEntity.ok().body(projectService.create(paylod, companyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@RequestHeader("company-id") Long companyId,
            @RequestHeader("user-id") Long userId, @PathVariable Long id, @RequestBody Project paylod) {
        return ResponseEntity.ok().body(projectService.update(id, companyId, paylod));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("company-id") Long companyId,
            @RequestHeader("user-id") Long userId, @PathVariable Long id) {
        projectService.delete(id, companyId);
        return ResponseEntity.noContent().build();
    }

}
