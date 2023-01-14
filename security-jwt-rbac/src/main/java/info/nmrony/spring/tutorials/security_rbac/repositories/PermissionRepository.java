package info.nmrony.spring.tutorials.security_rbac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
