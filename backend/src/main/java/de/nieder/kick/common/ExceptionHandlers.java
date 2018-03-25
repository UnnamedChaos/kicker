package de.nieder.kick.common;

import static java.util.stream.Collectors.toList;

import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.base.Strings;

@RestControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

	@Override
	@NonNull
	protected ResponseEntity<Object> handleExceptionInternal(@NonNull final Exception ex, @Nullable final Object body,
			@NonNull final HttpHeaders headers, @NonNull final HttpStatus status, @NonNull final WebRequest request) {
		LOGGER.error("{} happened. The according handler method should be overwritten.", ex.getClass().getName());
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@NonNull
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(@NonNull final MissingPathVariableException ex,
			@NonNull final HttpHeaders headers, @NonNull final HttpStatus status, @NonNull final WebRequest request) {
		LOGGER.error("Caught MissingPathVariableException on method {}: {}", ex.getParameter().getExecutable(),
				ex.getMessage());
		LogParameters.addResult(new LogParameters.Param(ex.getClass().getSimpleName(), ex.getMessage()));
		return new ResponseEntity<>(
				new ErrorResponse("internal-error", this.getRequestDescription(request), "Unexpected exception.",
						"An unexpected exception occurred during request processing."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	@NonNull
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			@NonNull final MissingServletRequestParameterException ex, @NonNull final HttpHeaders headers,
			@NonNull final HttpStatus status, @NonNull final WebRequest request) {
		LogParameters.addResult(new LogParameters.Param(ex.getClass().getSimpleName(), ex.getMessage()));

		return new ResponseEntity<>(
				new ErrorResponse("parameter-missed", this.getRequestDescription(request), "Parameters incomplete.",
						"The requested query does not contain all parameters. " + ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	@NonNull
	protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull final HttpMessageNotReadableException ex,
			@NonNull final HttpHeaders headers, @NonNull final HttpStatus status, @NonNull final WebRequest request) {
		LogParameters.addResult(new LogParameters.Param(ex.getClass().getSimpleName(), ex.getMessage()));

		return new ResponseEntity<>(
				new ErrorResponse("cannot-parse", this.getRequestDescription(request), "Cannot parse entity.",
						"The request entity body cannot be parsed as a valid document. " + ex.getMessage()),
				HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	@NonNull
	protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull final MethodArgumentNotValidException ex,
			@NonNull final HttpHeaders headers, @NonNull final HttpStatus status, @NonNull final WebRequest request) {
		LogParameters.addResult(new LogParameters.Param(ex.getClass().getSimpleName(), ex.getMessage()));

		BindingResult bindingResult = ex.getBindingResult();
		List<ApiErrorResponse.FieldError> apiFieldErrors = bindingResult.getFieldErrors().stream()
				.map(fieldError -> new ApiErrorResponse.FieldError(fieldError.getObjectName(), fieldError.getField(),
						fieldError.getCode(), fieldError.getRejectedValue()))
				.collect(toList());

		List<ApiErrorResponse.GlobalError> apiGlobalErrors = bindingResult.getGlobalErrors().stream().map(
				globalError -> new ApiErrorResponse.GlobalError(globalError.getObjectName(), globalError.getCode()))
				.collect(toList());

		ApiErrorResponse errorResponse = new ApiErrorResponse("parameters-invalid", this.getRequestDescription(request),
				"Parameter validation failed",
				"The parameter validation failed. Please see the remainder for validation details.", apiFieldErrors,
				apiGlobalErrors);

		return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	@NonNull
	protected ResponseEntity<Object> handleNoHandlerFoundException(@NonNull final NoHandlerFoundException ex,
			@NonNull final HttpHeaders headers, @NonNull final HttpStatus status, @NonNull final WebRequest request) {

		return new ResponseEntity<>(
				new ErrorResponse("operation-unknown", this.getRequestDescription(request), "Operation not found.",
						"The requested method and URI do not match a defined API operation. " + ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@NonNull
	private String getRequestDescription(@NonNull final WebRequest request) {
		if (request instanceof NativeWebRequest) {
			HttpServletRequest nativeRequest = ((NativeWebRequest) request).getNativeRequest(HttpServletRequest.class);
			if (null != nativeRequest) {
				StringBuilder buffer = new StringBuilder();
				buffer.append(nativeRequest.getMethod()).append(' ').append(nativeRequest.getRequestURI());
				if (!Strings.isNullOrEmpty(nativeRequest.getQueryString())) {
					buffer.append('?').append(nativeRequest.getQueryString());
				}
				return buffer.toString();
			}
		}
		return request.getDescription(false);
	}

	@SuppressWarnings("unused")
	@ExceptionHandler
	@NonNull
	ResponseEntity<ErrorResponse> handleBaseException(@NonNull final BaseException ex,
			@NonNull final WebRequest request) {
		LogParameters.addResult(new LogParameters.Param(ex.getClass().getSimpleName(), ex.getMessage()));
		return new ResponseEntity<>(new ErrorResponse(ex.getErrorCode(), this.getRequestDescription(request),
				ex.getErrorMessage(), ex.getErrorDescription()), ex.getResponseStatus());
	}

	@SuppressWarnings("unused")
	@ExceptionHandler
	@NonNull
	ResponseEntity<ErrorResponse> handleUnexpectedException(@NonNull final Exception ex,
			@NonNull final WebRequest request) throws Exception {
		// Let the framework handle Security related stuff.
		if (ex instanceof AccessDeniedException) {
			LogParameters.addResult(new LogParameters.Param(ex.getClass().getSimpleName(), ex.getMessage()));
			throw ex;
		}
		// If the exception is annotated with @ResponseStatus rethrow it and let the
		// framework handle it.
		if (null != AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class)) {
			throw ex;
		}
		LOGGER.error("Caught unexpected exception while processing request.", ex);
		LogParameters.addResult(new LogParameters.Param(ex.getClass().getSimpleName(), ex.getMessage()));
		return new ResponseEntity<>(
				new ErrorResponse("internal-error", this.getRequestDescription(request), "Unexpected exception.",
						"An unexpected exception occurred during request processing."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
