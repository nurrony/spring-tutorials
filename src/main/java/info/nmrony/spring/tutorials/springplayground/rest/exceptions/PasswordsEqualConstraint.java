package info.nmrony.spring.tutorials.springplayground.rest.exceptions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
public @interface PasswordsEqualConstraint {
    String message() default "{PasswordsEqualConstraint}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {
    @Override
    public void initialize(final PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(final Object subject, final ConstraintValidatorContext context) {
        try {
            final Method methodGetPassword = subject.getClass().getMethod("getPassword");
            final Method methodGetConfirmpassword = subject.getClass().getMethod("getConfirmPassword");

            if (methodGetPassword.invoke(subject) == null && methodGetConfirmpassword.invoke(subject) == null)
                return true;
            else if (methodGetPassword.invoke(subject) == null)
                return false;
            final boolean equals = methodGetPassword.invoke(subject).equals(methodGetConfirmpassword.invoke(subject));
            return equals;

        } catch (final NoSuchMethodException ex) {
            ex.printStackTrace();
            return false;
        } catch (final Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
