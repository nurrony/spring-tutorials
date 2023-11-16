package info.nmrony.spring.tutorials.security_rbac.responses;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Api Success Response Contract
 *
 * @author Nur Rony<pro.nmrony@gmail.com>
 */
public class SuccessResponse<T> {

    @Builder(builderMethodName = "SimpleResponse")
    public static SimpleResponse withSimpleResponse(final String message) {
        return new SimpleResponse(200, message);
    }

    @Builder(builderMethodName = "WithResource")
    public static <T> WithResource<T> withResourceResponse(
            final String message, final T data) {
        return new WithResource<>(200, message, data);
    }

    @Builder(builderMethodName = "WithMixedContentResource")
    public static <T, E> WithMixedContentResource<T, E> withMixedContentResourceResponse(
            final String message, final T data, final E errors) {
        return new WithMixedContentResource<>(207, message, data, errors);
    }

    @Builder(builderMethodName = "WithPaginatedResourceCollection")
    public static <T> WithPaginatedResourceCollection<T> withResourceResponse(
            final String message, final int page, final int size, final int totalPage,
            final long totalRecord, final T data) {
        return new WithPaginatedResourceCollection<>(200, message, page, size, totalPage, totalRecord, data);
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class SimpleResponse extends ApiSuccessResponse {
        int statusCode;
        String message;
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class WithResource<T> extends ApiSuccessResponse {
        int statusCode;
        String message;
        T data;
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class WithMixedContentResource<T, E> extends ApiSuccessResponse {
        int statusCode;
        String message;
        T data;
        E errors;
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class WithPaginatedResourceCollection<T> extends ApiSuccessResponse {
        int statusCode;
        String message;
        int page;
        int size;
        int totalPage;
        long totalRecord;
        T data;
    }

}
