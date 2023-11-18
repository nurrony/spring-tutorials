package info.nmrony.spring.tutorials.security_rbac.configs.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static long JWT_TOKEN_VALIDITY_SECONDS;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.security.jwt.issuer}")
    private String jwtIssuer;

    @Value("${application.security.jwt.validity-in-seconds}")
    public void setJwtValidity(final long seconds) {
        log.info("setting jwt expire token {}", seconds);
        JwtTokenProvider.JWT_TOKEN_VALIDITY_SECONDS = seconds;
    }

    public String generateToken(final Authentication authentication) {
        final Map<String, Object> claims = new HashMap<>();
        final UserDetails user = (UserDetails) authentication.getPrincipal();
        final var roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("ROLES", roles);
        return generateToken(_generateClaims(claims, user.getUsername()));
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(final String token) {
        final List<String> roles = getClaimFromToken(token, claims -> (List<String>) claims.get("ROLES"));
        return roles;
    }

    public String getUserId(final String token) {
        return getClaimFromToken(token, Claims::getSubject).split(",")[0];
    }

    // retrieve username from jwt token
    public String getUsername(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getUserEmail(final String token) {
        return getClaimFromToken(token, Claims::getSubject).split(",")[2];

    }

    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDate(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Authentication getAuthentication(final String token) {
        final UserDetails principal = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public boolean validate(final String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (final MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (final ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (final UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (final IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

    public Authentication authenticate(final String username, final String password) throws Exception {
        final UserDetails principal = userDetailsService.loadUserByUsername(username);
        final Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().verifyWith(getSigningKey(jwtSecret)).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSigningKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private String generateToken(final Claims claims) {
        return Jwts.builder().claims(claims).signWith(getSigningKey(jwtSecret)).compact();
    }

    private Claims _generateClaims(Map<String, ?> claims, String subject) {
        return Jwts.claims()
                .add(claims)
                .subject(subject)
                .issuer(jwtIssuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_SECONDS * 1000))
                .build();
    }

}
