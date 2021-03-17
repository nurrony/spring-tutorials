package info.nmrony.spring.tutorials.springplayground.domain.responses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    public static SimpleResponse newSimpleResponse(int statusCode, String message) {
        return new SimpleResponse(statusCode, message);
    }

    @Builder(builderMethodName = "WithResource")
    public static <T> WithResource<T> withResourceResponse(@NotBlank int statusCode, @NotBlank String message,
            @NotNull T data) {
        return new WithResource<>(statusCode, message, data);
    }

    @Builder(builderMethodName = "WithPaginatedResourceCollection")
    public static <T> WithPaginatedResourceCollection<T> withResourceResponse(@NotBlank int statusCode,
            @NotBlank String message, int page, @NotNull int size, @NotNull int totalPage, @NotNull long totalRecord,
            @NotNull T data) {
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
