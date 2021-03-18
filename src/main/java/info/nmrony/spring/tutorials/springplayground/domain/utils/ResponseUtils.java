package info.nmrony.spring.tutorials.springplayground.domain.utils;

import java.util.Collection;

import org.springframework.data.domain.Page;

import info.nmrony.spring.tutorials.springplayground.domain.responses.SuccessResponse;
import info.nmrony.spring.tutorials.springplayground.domain.responses.SuccessResponse.WithPaginatedResourceCollection;
import info.nmrony.spring.tutorials.springplayground.domain.responses.SuccessResponse.WithResource;

final public class ResponseUtils {
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
                .statusCode(statusCode).message(message).data(page.getContent()).size(page.getSize())
                .totalPage(page.getTotalPages()).totalRecord(page.getTotalElements()).build();
    }
}
