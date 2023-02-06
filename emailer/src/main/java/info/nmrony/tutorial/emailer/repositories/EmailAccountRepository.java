package info.nmrony.tutorial.emailer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import info.nmrony.tutorial.emailer.domain.entities.EmailAccount;

public interface EmailAccountRepository extends JpaRepository<EmailAccount, Long> {
}
