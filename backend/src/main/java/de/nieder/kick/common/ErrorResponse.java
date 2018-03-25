package de.nieder.kick.common;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class ErrorResponse {
	@NonNull
	private final String code;

	@NonNull
	private final String called;

	@NonNull
	private final String message;

	@Nullable
	private final String description;

	/**
	 * Constructor.
	 *
	 * @param code
	 *            the value for {@link #code}
	 * @param called
	 *            the value for {@link #called}
	 * @param message
	 *            the value for {@link #message}
	 * @param description
	 *            the value for {@link #description}
	 */
	public ErrorResponse(@NonNull final String code, @NonNull String called, @NonNull final String message,
			@Nullable final String description) {
		this.code = code;
		this.called = called;
		this.message = message;
		this.description = description;
	}

	/**
	 * Field access for {@link #code}.
	 *
	 * @return the value of {@link #code}.
	 */
	@SuppressWarnings("unused") // used by JSON serializer
	@NonNull
	public String getCode() {
		return this.code;
	}

	/**
	 * Field access for {@link #called}.
	 *
	 * @return the value of {@link #called}.
	 */
	@SuppressWarnings("unused") // used by JSON serializer
	@NonNull
	public String getCalled() {
		return this.called;
	}

	/**
	 * Field access for {@link #message}.
	 *
	 * @return the value of {@link #message}.
	 */
	@SuppressWarnings("unused") // used by JSON serializer
	@NonNull
	public String getMessage() {
		return this.message;
	}

	/**
	 * Field access for {@link #description}.
	 *
	 * @return the value of {@link #description}.
	 */
	@SuppressWarnings("unused") // used by JSON serializer
	@Nullable
	public String getDescription() {
		return this.description;
	}
}