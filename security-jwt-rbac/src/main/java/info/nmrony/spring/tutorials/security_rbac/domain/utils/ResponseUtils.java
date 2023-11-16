package info.nmrony.spring.tutorials.security_rbac.domain.utils;

import java.util.Collection;

import org.springframework.data.domain.Page;

import info.nmrony.spring.tutorials.security_rbac.responses.SuccessResponse;
import info.nmrony.spring.tutorials.security_rbac.responses.SuccessResponse.WithMixedContentResource;
import info.nmrony.spring.tutorials.security_rbac.responses.SuccessResponse.WithPaginatedResourceCollection;
import info.nmrony.spring.tutorials.security_rbac.responses.SuccessResponse.WithResource;

final public class ResponseUtils {

    private ResponseUtils() {
        throw new IllegalStateException("ResponseUtils is a utitily class. You can not instantiate it.");
    }

    final public static SuccessResponse.SimpleResponse buildResponse(String message) {
        return SuccessResponse.SimpleResponse().message(message).build();
    }

    @SuppressWarnings("unchecked")
    final public static <T> SuccessResponse.WithResource<T> buildResourceResponse(Collection<T> data, String message) {
        return (WithResource<T>) SuccessResponse.WithResource().message(message).data(data).build();
    }

    @SuppressWarnings("unchecked")
    final public static <T> SuccessResponse.WithResource<T> buildResourceResponse(T data, String message) {
        return (WithResource<T>) SuccessResponse.WithResource().message(message).data(data).build();
    }

    @SuppressWarnings("unchecked")
    final public static <T, E> SuccessResponse.WithMixedContentResource<T, E> buildMixedContentResponse(T data,
            E errors, String message) {
        return (WithMixedContentResource<T, E>) SuccessResponse.WithMixedContentResource()
                .message(message)
                .data(data)
                .errors(errors)
                .build();
    }

    @SuppressWarnings("unchecked")
    final public static <T> SuccessResponse.WithPaginatedResourceCollection<T> buildPaginatedResponse(Page<T> page,
            String message) {
        return (WithPaginatedResourceCollection<T>) SuccessResponse.WithPaginatedResourceCollection()
                .message(message)
                .data(page.getContent())
                .page(page.getPageable().getPageNumber())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .totalRecord(page.getTotalElements())
                .build();
    }
}
