package info.nurrony.tutorials.spring.overriderest.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Api Success Response
 *
 * @author Nur Rony
 */
public class SuccessResponse<T> {

    @Builder(builderMethodName = "SimpleResponse")
    public static SimpleResponse newSimpleResponse(final int statusCode, final String message) {
        return new SimpleResponse(statusCode, message);
    }

    @Builder(builderMethodName = "WithResource")
    public static <T> WithResource<T> withResourceResponse(final int statusCode,
            final String message, final T data) {
        return new WithResource<>(statusCode, message, data);
    }

    @Builder(builderMethodName = "WithPaginatedResourceCollection")
    public static <T> WithPaginatedResourceCollection<T> withResourceResponse(final int statusCode,
            final String message, final int limit, final int offset, final int totalPage,
            final long totalRecord, final T data) {
        return new WithPaginatedResourceCollection<>(statusCode, message, limit, offset, totalPage, totalRecord, data);
    }

    @Value
    @EqualsAndHashCode()
    public static class SimpleResponse {
        int statusCode;
        String message;
    }

    @Value
    @EqualsAndHashCode()
    public static class WithResource<T> {
        int statusCode;
        String message;
        T data;
    }

    @Value
    @EqualsAndHashCode()
    public static class WithPaginatedResourceCollection<T> {
        int statusCode;
        String message;
        int limit;
        int offset;
        int totalPage;
        long totalRecord;
        T data;
    }

}
