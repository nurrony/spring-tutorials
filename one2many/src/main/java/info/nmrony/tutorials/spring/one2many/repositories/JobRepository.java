package info.nmrony.tutorials.spring.one2many.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.nmrony.tutorials.spring.one2many.domain.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByIdAndCompanyId(Long id, Long companyId);
}
