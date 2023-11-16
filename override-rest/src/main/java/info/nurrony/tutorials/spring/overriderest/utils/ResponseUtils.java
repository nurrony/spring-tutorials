package info.nurrony.tutorials.spring.overriderest.utils;

import java.util.Collection;

import info.nurrony.tutorials.spring.overriderest.core.SuccessResponse;
import info.nurrony.tutorials.spring.overriderest.core.SuccessResponse.WithPaginatedResourceCollection;
import info.nurrony.tutorials.spring.overriderest.core.SuccessResponse.WithResource;
import info.nurrony.tutorials.spring.overriderest.dto.Page;

final public class ResponseUtils {

    private ResponseUtils() {
        throw new IllegalStateException("ResponseUtils is a utitily class. You can not instantiate it.");
    }

    final public static SuccessResponse.SimpleResponse buildResponse(String message, int statusCode) {
        return SuccessResponse.SimpleResponse().message(message).statusCode(statusCode).build();
    }

    @SuppressWarnings("unchecked")
    final public static <T> SuccessResponse.WithResource<T> buildResourceResponse(Collection<T> data, String message,
            int statusCode) {

        return (WithResource<T>) SuccessResponse.WithResource().statusCode(statusCode).message(message).data(data)
                .build();
    }

    @SuppressWarnings("unchecked")
    final public static <T> SuccessResponse.WithResource<T> buildResourceResponse(T data, String message,
            int statusCode) {
        return (WithResource<T>) SuccessResponse.WithResource().statusCode(statusCode).message(message).data(data)
                .build();
    }

    @SuppressWarnings("unchecked")
    final public static <T> SuccessResponse.WithPaginatedResourceCollection<T> buildPaginatedResponse(Page<T> page,
            String message, int statusCode) {
        return (WithPaginatedResourceCollection<T>) SuccessResponse.WithPaginatedResourceCollection()
                .statusCode(statusCode)
                .message(message)
                .data(page.items())
                .limit(page.limit())
                .offset(page.offset())
                .totalPage(page.totalPage())
                .totalRecord(page.totalResult())
                .build();
    }
}
