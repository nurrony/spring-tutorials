package info.nurrony.tutorials.spring.eventrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import info.nurrony.tutorials.spring.eventrestapi.domain.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
