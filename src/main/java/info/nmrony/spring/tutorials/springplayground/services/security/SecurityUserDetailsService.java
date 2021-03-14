package info.nmrony.spring.tutorials.springplayground.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import info.nmrony.spring.tutorials.springplayground.domain.entities.User;
import info.nmrony.spring.tutorials.springplayground.domain.exceptions.ResourceNotFoundException;
import info.nmrony.spring.tutorials.springplayground.domain.security.SecurityUserDetails;
import info.nmrony.spring.tutorials.springplayground.services.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(User.class, username));
    return SecurityUserDetails.fromEntity(user);
  }
}
