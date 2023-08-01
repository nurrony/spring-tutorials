package info.nmrony.tutorials.spring.batch.repositories;

import info.nmrony.tutorials.spring.batch.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
