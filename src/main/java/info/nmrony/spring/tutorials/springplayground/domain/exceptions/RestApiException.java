package info.nmrony.spring.tutorials.springplayground.domain.exceptions;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CUSTOM;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.Data;

@Data
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
@JsonTypeInfo(include = WRAPPER_OBJECT, use = CUSTOM, property = "error", visible = true)
public class RestApiException {

    private HttpStatus name;
    private int statusCode;
    private String message;
    private String debugMessage;

    @JsonProperty("details")
    private List<ApiException> validationErrors;

    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private RestApiException() {
        timestamp = LocalDateTime.now();
    }

    public RestApiException(final HttpStatus status) {
        this();
        this.name = status;
        this.statusCode = status.value();
    }

    public RestApiException(final HttpStatus status, final Throwable ex) {
        this();
        this.statusCode = status.value();
        this.name = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public RestApiException(final HttpStatus status, final String message, final Throwable ex) {
        this();
        this.name = status;
        this.message = message;
        this.statusCode = status.value();
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addValidationErrorDetails(final ApiException validationError) {
        if (validationErrors == null) {
            validationErrors = new ArrayList<ApiException>();
        }
        validationErrors.add(validationError);
    }

    private void addValidationError(final String object, final String field, final Object rejectedValue,
            final String message) {
        addValidationErrorDetails(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(final String object, final String message) {
        addValidationErrorDetails(new ApiValidationError(object, message));
    }

    private void addValidationError(final FieldError fieldError) {
        this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(final List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(final ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public void addValidationError(final List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when
     * a @Validated validation fails.
     *
     * @param constrainViolation the ConstraintViolation
     */
    private void addValidationError(final ConstraintViolation<?> constrainViolation) {
        this.addValidationError(constrainViolation.getRootBeanClass().getSimpleName(),
                ((PathImpl) constrainViolation.getPropertyPath()).getLeafNode().asString(),
                constrainViolation.getInvalidValue(), constrainViolation.getMessage());
    }

    public void addValidationErrors(final Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

}
