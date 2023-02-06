package info.nmrony.tutorial.emailer.domain.exceptions;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2830835477122881861L;

    public ResourceNotFoundException(final Class<?> clazz, final Object... searchParams) {
        super(ResourceNotFoundException.generateMessage(clazz.getSimpleName(),
                Arrays.stream(searchParams).map(Object::toString).collect(Collectors.joining(", "))));
    }

    private static String generateMessage(final String entity, String searchParams) {
        return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
    }

}
