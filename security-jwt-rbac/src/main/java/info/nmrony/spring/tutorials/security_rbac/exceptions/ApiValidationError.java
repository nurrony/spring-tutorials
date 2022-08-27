package info.nmrony.spring.tutorials.security_rbac.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiValidationError implements ApiException {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(final String object, final String message) {
        this.object = object;
        this.message = message;
    }
}
