package info.nmrony.spring.tutorials.security_rbac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
