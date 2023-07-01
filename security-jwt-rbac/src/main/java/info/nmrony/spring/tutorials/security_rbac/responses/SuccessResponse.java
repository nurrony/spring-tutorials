package info.nmrony.spring.tutorials.security_rbac.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    public static <T> WithResource<T> withResourceResponse(@NotBlank final int statusCode,
            @NotBlank final String message, @NotNull final T data) {
        return new WithResource<>(statusCode, message, data);
    }

    @Builder(builderMethodName = "WithPaginatedResourceCollection")
    public static <T> WithPaginatedResourceCollection<T> withResourceResponse(@NotBlank final int statusCode,
            @NotBlank final String message, final int page, @NotNull final int size, @NotNull final int totalPage,
            @NotNull final long totalRecord, @NotNull final T data) {
        return new WithPaginatedResourceCollection<>(statusCode, message, page, size, totalPage, totalRecord, data);
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
