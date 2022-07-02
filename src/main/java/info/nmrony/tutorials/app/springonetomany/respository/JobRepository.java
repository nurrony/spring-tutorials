package info.nmrony.tutorials.app.springonetomany.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.nmrony.tutorials.app.springonetomany.domain.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

  Optional<Job> findByIdAndCompanyId(Long id, Long companyId);
}
