package info.nmrony.spring.tutorials.security_rbac.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.User;
import info.nmrony.spring.tutorials.security_rbac.domain.models.security.SecurityUserDetails;
import info.nmrony.spring.tutorials.security_rbac.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, username));
        return SecurityUserDetails.fromEntity(user);
    }
}
