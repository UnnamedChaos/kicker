package de.nieder.kick.common;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class BaseException extends RuntimeException {
	/**
	 * Constructor.
	 */
	protected BaseException() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *            forwarded to super constructor
	 */
	protected BaseException(final String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *            forwarded to super constructor
	 */
	protected BaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *            forwarded to super constructor
	 * @param cause
	 *            forwarded to super constructor
	 */
	protected BaseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Get the HTTP response status.
	 *
	 * @return the HTTP response status to be returned to the caller.
	 */
	@NonNull
	public abstract HttpStatus getResponseStatus();

	/**
	 * Get the error code to be sent as {@link ErrorResponse#code} to the caller.
	 *
	 * @return the error code
	 */
	@NonNull
	public abstract String getErrorCode();

	/**
	 * Gets a short error message to be sent as {@link ErrorResponse#message} to the
	 * caller.
	 *
	 * @return the message
	 */
	@NonNull
	public abstract String getErrorMessage();

	/**
	 * Gets a descriptive text to be sent as {@link ErrorResponse#description} to
	 * the caller.
	 *
	 * @return the error description
	 */
	@Nullable
	public abstract String getErrorDescription();
}
