package info.nmrony.spring.tutorials.springplayground.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nur Rony
 */
public class JsonResponseBodyHandler<T> implements HttpResponse.BodyHandler<Supplier<T>> {

    private final Class<T> clazz;

    public JsonResponseBodyHandler(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public HttpResponse.BodySubscriber<Supplier<T>> apply(final HttpResponse.ResponseInfo responseInfo) {
        return asJSON(clazz);
    }

    public static <T> HttpResponse.BodySubscriber<Supplier<T>> asJSON(final Class<T> targetType) {
        final HttpResponse.BodySubscriber<InputStream> upstream = HttpResponse.BodySubscribers.ofInputStream();

        return HttpResponse.BodySubscribers.mapping(upstream, inputStream -> toSupplierOfType(inputStream, targetType));
    }

    public static <T> Supplier<T> toSupplierOfType(final InputStream inputStream, final Class<T> targetType) {
        return () -> {
            try (InputStream stream = inputStream) {
                final ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(stream, targetType);
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

}
