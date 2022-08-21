package info.nmrony.spring.tutorials.security_rbac.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author Nur Rony
 */
public final class AppUtils {

    private AppUtils() {
        throw new IllegalStateException("Application Utility Class");
    }

    final public static boolean isMapValueNullOrEmpty(final Map.Entry<?, String> entry) {
        return entry.getValue() != null && !entry.getValue().isEmpty();
    }

    final public static boolean isStringNullOrEmpty(final String str) {
        return str != null && str.isBlank();
    }

    final public static boolean isCollectionNullOrEmpty(final Collection<?> collection) {
        return collection != null && collection.isEmpty();
    }

    final public static boolean isNotNull(final Object item) {
        return item != null;
    }

}
