package info.nmrony.tutorials.app.springonetomany.respository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import info.nmrony.tutorials.app.springonetomany.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  Optional<Project> findByIdAndCompanyId(Long id, Long companyId);

  Set<Project> findByCompanyId(Long companyId);

  void deleteByIdAndCompanyId(Long id, Long companyId);

}
