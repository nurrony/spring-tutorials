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
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(Object subject, ConstraintValidatorContext context) {
        try {
            Method methodGetPassword = subject.getClass().getMethod("getPassword");
            Method methodGetConfirmpassword = subject.getClass().getMethod("getConfirmPassword");

            if (methodGetPassword.invoke(subject) == null && methodGetConfirmpassword.invoke(subject) == null)
                return true;
            else if (methodGetPassword.invoke(subject) == null)
                return false;
            return methodGetPassword.invoke(subject).equals(methodGetConfirmpassword.invoke(subject));

        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
