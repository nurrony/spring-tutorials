package info.nmrony.spring.tutorials.security_rbac.exceptions;
package com.investar.oms.broker.core.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements ApiException {

    private ResponseEntity<Object> buildResponseEntity(final RestApiException apiError) {
        return new ResponseEntity<>(apiError, apiError.getName());
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(new RestApiException(HttpStatus.REQUEST_TIMEOUT, ex.getMessage(), ex));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {
        return buildResponseEntity(
                new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(new RestApiException(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
     * invalid as well.
     *
     * @param exception HttpMediaTypeNotSupportedException
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the Error object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(exception.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        exception.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new RestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                builder.substring(0, builder.length() - 2), exception));
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is
     * malformed.
     *
     * @param exception HttpMessageNotReadableException
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the Error object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        final String error = "Malformed JSON request";
        return buildResponseEntity(new RestApiException(HttpStatus.BAD_REQUEST, error, exception));
    }

    /**
     * Handle HttpMessageNotWritableException. Happens when request JSON is
     * malformed.
     *
     * @param exception HttpMessageNotWritableException
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the Error object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final String error = "Error writing JSON output";
        return buildResponseEntity(new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(
                new RestApiException(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed", exception));
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(
                new RestApiException(HttpStatus.BAD_REQUEST, "Max upload size exceeded", exception));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(final DataIntegrityViolationException exception,
            final WebRequest request) {
        if (exception.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(
                    new RestApiException(HttpStatus.CONFLICT, "Database error", exception.getCause()));
        }
        return buildResponseEntity(new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR, exception));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
     * validation.
     *
     * @param exception the MethodArgumentNotValidException that is thrown
     *                  when @Valid validation fails
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the Error object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final RestApiException apiError = new RestApiException(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
        apiError.addValidationError(exception.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodValidationException(MethodValidationException exception,
            HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        final RestApiException apiError = new RestApiException(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");

        return buildResponseEntity(apiError);
    }

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required'
     * request parameter is missing.
     *
     * @param exception MissingServletRequestParameterException
     * @param headers   HttpHeaders
     * @param status    HttpStatus
     * @param request   WebRequest
     * @return the Error object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(
                new RestApiException(HttpStatus.BAD_REQUEST, ex.getParameterName() + " parameter is missing", ex));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(
                new RestApiException(HttpStatus.BAD_REQUEST, ex.getRequestPartName() + " part is missing", ex));
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param exception
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception,
            HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        final RestApiException apiError = new RestApiException(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", exception.getHttpMethod(),
                exception.getRequestURL()));
        apiError.setDebugMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     *
     */
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final RestApiException apiError = new RestApiException(HttpStatus.NOT_FOUND);
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles NotFoundException. Created to encapsulate errors with more detail
     * than javax.persistence.NotFoundException.
     *
     * @param exception the NotFoundException
     * @return the Error object
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(final ResourceNotFoundException exception) {
        final RestApiException apiError = new RestApiException(HttpStatus.NOT_FOUND);
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(
                new RestApiException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param exception the Exception
     * @return the Error object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException exception, final WebRequest request) {
        final RestApiException apiError = new RestApiException(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                exception.getName(), exception.getValue(), exception.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        return buildResponseEntity(
                new RestApiException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
    }

    /**
     * Javax Validation EntityNotFound Exception Handler
     *
     * @param EntityNotFoundException exception
     * @return
     */
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(final jakarta.persistence.EntityNotFoundException exception) {
        return buildResponseEntity(new RestApiException(HttpStatus.NOT_FOUND, exception));
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
     * fails.
     *
     * @param exception the ConstraintViolationException
     * @return the Error object
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException exception) {
        final RestApiException apiError = new RestApiException(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(exception.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

}

