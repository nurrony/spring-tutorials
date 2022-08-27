package info.nmrony.spring.tutorials.security_rbac.domain.constants;

public final class Roles {
    private Roles() {
        throw new IllegalStateException("Intantialization is not allowed");
    }

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String MODERATOR = "ROLE_MODERATOR";
    public static final String USER = "ROLE_USER";
}
