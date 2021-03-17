package info.nmrony.spring.tutorials.springplayground.rest.exceptions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.stereotype.Component;

import info.nmrony.spring.tutorials.springplayground.services.UserService;
import lombok.RequiredArgsConstructor;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Constraint(validatedBy = UsernameExistConstraintValidator.class)
public @interface UsernameExistConstraint {
    String message() default "{UsernameExistConstraint}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

@Component
@RequiredArgsConstructor
class UsernameExistConstraintValidator implements ConstraintValidator<UsernameExistConstraint, String> {
    private final UserService userService;

    @Override
    public void initialize(UsernameExistConstraint arg0) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext arg1) {
        return Objects.isNull(username) || !userService.findByUsername(username).isPresent();
    }
}
