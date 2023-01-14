package info.nmrony.spring.tutorials.security_rbac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
