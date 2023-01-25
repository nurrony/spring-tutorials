package info.nmrony.spring.tutorials.security_rbac.exceptions;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements ApiException {

    private ResponseEntity<Object> buildResponseEntity(final RestApiException apiError) {
        return new ResponseEntity<>(apiError, apiError.getName());
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
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException exception, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        return buildResponseEntity(new RestApiException(HttpStatus.BAD_REQUEST,
                exception.getParameterName() + " parameter is missing", exception));
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
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(exception.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        exception.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new RestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                builder.substring(0, builder.length() - 2), exception));
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final RestApiException apiError = new RestApiException(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
        apiError.addValidationError(exception.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
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
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        final String error = "Malformed JSON request";
        return buildResponseEntity(new RestApiException(HttpStatus.BAD_REQUEST, error, exception));
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
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException exception,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final RestApiException apiError = new RestApiException(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", exception.getHttpMethod(),
                exception.getRequestURL()));
        apiError.setDebugMessage(exception.getMessage());
        return buildResponseEntity(apiError);
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

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param exception the Exception
     * @return the Error object
     */
    @SuppressWarnings("null")
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException exception, final WebRequest request) {
        final RestApiException apiError = new RestApiException(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                exception.getName(), exception.getValue(), exception.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Javax Validation EntityNotFound Exception Handler
     *
     * @param EntityNotFoundException exception
     * @return
     */
    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(final javax.persistence.EntityNotFoundException exception) {
        return buildResponseEntity(new RestApiException(HttpStatus.NOT_FOUND, exception));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(final HttpMessageNotWritableException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = "Error writing JSON output";
        return buildResponseEntity(new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException exception, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        return buildResponseEntity(
                new RestApiException(HttpStatus.METHOD_NOT_ALLOWED, "NMR Method not allowed", exception));
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
     * AccessDeniedException handler from Spring Security
     *
     * @param request
     * @param exception
     * @return ResApiException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(final HttpServletRequest request,
            final AccessDeniedException exception) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), exception);

        return buildResponseEntity(
                new RestApiException(HttpStatus.FORBIDDEN, "You are not allowed to access this endpoint", exception));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(final HttpServletRequest request,
            final BadCredentialsException exception) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), exception);
        return buildResponseEntity(
                new RestApiException(HttpStatus.UNAUTHORIZED, "credential is not correct.", exception));
    }

    /**
     * Handles NotFoundException. Created to encapsulate errors with more detail
     * than javax.persistence.NotFoundException.
     *
     * @param exception the NotFoundException
     * @return the Error object
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(final HttpServletRequest request,
            final MalformedJwtException exception) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), exception);

        return buildResponseEntity(
                new RestApiException(HttpStatus.UNAUTHORIZED, "Full authentication is required", exception));
    }

    /**
     * AuthenticationException handler from Spring Security
     *
     * @param request
     * @param exception
     * @return ResApiException
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(final HttpServletRequest request,
            final MalformedJwtException exception) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), exception);

        return buildResponseEntity(
                new RestApiException(HttpStatus.UNAUTHORIZED, "Full authentication is required", exception));
    }

    /**
     * Handles InsufficientAuthenticationException.
     *
     * @param exception the InsufficientAuthenticationException
     * @return the Error object
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Object> handleAccessDeniedException(final HttpServletRequest request,
            final InsufficientAuthenticationException exception) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), exception);

        return buildResponseEntity(
                new RestApiException(HttpStatus.FORBIDDEN, "You are not allowed to access this endpoint", exception));
    }

    /**
     * Act as fallback exception handler.
     *
     * @param exception the InsufficientAuthenticationException
     * @return the Error object
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(final Exception exception, final WebRequest request) {
        exception.printStackTrace();
        return buildResponseEntity(
                new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "something went wrong!! please contact with administrator.  ", exception));
    }

}
