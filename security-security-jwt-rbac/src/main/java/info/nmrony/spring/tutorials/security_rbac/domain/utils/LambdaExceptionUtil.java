package info.nmrony.spring.tutorials.security_rbac.domain.utils;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Nur Rony
 * @apiNote Ideas taken form
 *          http://www.philandstuff.com/2012/04/28/sneakily-throwing-checked-exceptions.html
 */
public final class LambdaExceptionUtil {

    private LambdaExceptionUtil() {
        throw new IllegalStateException("LambdaExceptionUtil is an utility class. Instantiation is not allowed!");
    }

    @FunctionalInterface
    public interface Consumer_WithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface Function_WithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    /**
     *
     * @example .forEach(rethrowConsumer(name ->
     *          System.out.println(Class.forName(name))));
     */
    public static <T, E extends Exception> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T, E> consumer)
            throws E {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception exception) {
                throwActualException(exception);
            }
        };
    }

    /**
     * .map(rethrowFunction(name -> Class.forName(name))) or
     * .map(rethrowFunction(Class::forName))
     */
    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(Function_WithExceptions<T, R, E> function)
            throws E {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throwActualException(exception);
                return null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void throwActualException(Exception exception) throws E {
        throw (E) exception;
    }

}
