package info.nmrony.tutorials.spring.batch.repositories;

import info.nmrony.tutorials.spring.batch.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Customer, String> {
}
