package info.nmrony.spring.tutorials.springplayground.domain.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import info.nmrony.spring.tutorials.springplayground.domain.entities.User;
import lombok.Getter;

@Getter
public class SecurityUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String email;
    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;
    private boolean isCredentialsNonExpired;

    private Collection<? extends GrantedAuthority> authorities;

    // use Mapper
    public static UserDetails fromEntity(final User user) {
        final SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.email = user.getEmail();
        securityUserDetails.username = user.getUsername();
        securityUserDetails.password = user.getPassword();
        securityUserDetails.isEnabled = user.isEnabled();
        securityUserDetails.isAccountNonExpired = user.isAccountNonExpired();
        securityUserDetails.isAccountNonLocked = user.isAccountNonLocked();
        securityUserDetails.isCredentialsNonExpired = user.isCredentialsNonExpired();
        securityUserDetails.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return securityUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
