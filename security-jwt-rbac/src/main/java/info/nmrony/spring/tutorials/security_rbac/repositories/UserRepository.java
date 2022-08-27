package info.nmrony.spring.tutorials.security_rbac.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.User;
import info.nmrony.spring.tutorials.security_rbac.exceptions.ResourceNotFoundException;

@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    @CacheEvict(allEntries = true)
    <S extends User> List<S> saveAll(Iterable<S> entities);

    @Caching(evict = { @CacheEvict(key = "#p0.id"), @CacheEvict(key = "#p0.username") })
    <S extends User> S save(S entity);

    @Cacheable
    Optional<User> findById(Long id);

    @Cacheable
    default User getById(final Long id) {
        final Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(User.class, id);
        }
        if (!optionalUser.get().isEnabled()) {
            throw new ResourceNotFoundException(User.class, id);
        }
        return optionalUser.get();
    }

    @Cacheable
    Optional<User> findWithRolesByUsername(String username);

    @Cacheable
    Optional<User> findByUsername(String username);

    @Cacheable
    Page<User> findAll(Pageable pageable);

}
