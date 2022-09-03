package info.nmrony.tutorials.spring.one2many.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import info.nmrony.tutorials.spring.one2many.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Set<Project> findByCompanyId(Long companyId);

    void deleteByIdAndCompanyId(Long id, Long companyId);

    Optional<Project> findByIdAndCompanyId(Long id, Long companyId);
}
