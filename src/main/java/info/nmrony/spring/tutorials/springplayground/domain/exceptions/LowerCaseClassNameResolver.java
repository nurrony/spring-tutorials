package info.nmrony.spring.tutorials.springplayground.domain.exceptions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

public class LowerCaseClassNameResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(final Object value) {
        final String lowerCasedClassName = value.getClass().getSimpleName().toLowerCase();
        if (lowerCasedClassName.equals("restapiexception")) {
            return "error";
        }
        return value.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String idFromValueAndType(final Object value, final Class<?> suggestedType) {
        return idFromValue(value);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
