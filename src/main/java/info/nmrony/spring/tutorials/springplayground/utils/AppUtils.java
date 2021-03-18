package info.nmrony.spring.tutorials.springplayground.utils;

import java.util.Collection;
import java.util.Map;

public final class AppUtils {

    private AppUtils() {
        throw new IllegalStateException("Application Utility Class");
    }

    final public static boolean isMapValueNullOrEmpty(Map.Entry<?, String> entry) {
        return entry.getValue() != null && !entry.getValue().isEmpty();
    }

    final public static boolean isStringNullOrEmpty(String str) {
        return str != null && str.isBlank();
    }

    final public static boolean isCollectionNullOrEmpty(Collection<?> collection) {
        return collection != null && collection.isEmpty();
    }

    final public static boolean isNotNull(Object item) {
        return item != null;
    }

}
