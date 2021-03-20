package info.nmrony.spring.tutorials.springplayground.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.nmrony.spring.tutorials.springplayground.domain.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
