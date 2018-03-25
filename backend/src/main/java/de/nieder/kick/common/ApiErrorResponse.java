package de.nieder.kick.common;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class ApiErrorResponse extends ErrorResponse {
	@NonNull
	private final List<FieldError> fieldErrors;
	@NonNull
	private final List<GlobalError> globalErrors;

	ApiErrorResponse(@NonNull String code, @NonNull String path, @NonNull String message, @Nullable String description,
			@NonNull List<FieldError> fieldErrors, @NonNull List<GlobalError> globalErrors) {
		super(code, path, message, description);
		this.fieldErrors = fieldErrors;
		this.globalErrors = globalErrors;
	}

	@SuppressWarnings("unused") // used by JSON serializer
	@NonNull
	public List<FieldError> getFieldErrors() {
		return this.fieldErrors;
	}

	@SuppressWarnings("unused") // used by JSON serializer
	@NonNull
	public List<GlobalError> getGlobalErrors() {
		return this.globalErrors;
	}

	public static final class FieldError {
		@NonNull
		private final String objectName;

		@NonNull
		private final String field;

		@Nullable
		private final String code;

		@Nullable
		private final Object rejectedValue;

		FieldError(@NonNull String objectName, @NonNull String field, @Nullable String code,
				@Nullable Object rejectedValue) {
			this.objectName = objectName;
			this.field = field;
			this.code = code;
			this.rejectedValue = rejectedValue;
		}

		@SuppressWarnings("unused") // used by JSON serializer
		@NonNull
		public String getObjectName() {
			return this.objectName;
		}

		@SuppressWarnings("unused") // used by JSON serializer
		@NonNull
		public String getField() {
			return this.field;
		}

		@SuppressWarnings("unused") // used by JSON serializer
		@Nullable
		public String getCode() {
			return this.code;
		}

		@SuppressWarnings("unused") // used by JSON serializer
		@Nullable
		public Object getRejectedValue() {
			return this.rejectedValue;
		}
	}

	public static final class GlobalError {
		@NonNull
		private final String objectName;

		@Nullable
		private final String code;

		GlobalError(@NonNull String objectName, @Nullable String code) {
			this.objectName = objectName;
			this.code = code;
		}

		@SuppressWarnings("unused") // used by JSON serializer
		@NonNull
		public String getObjectName() {
			return this.objectName;
		}

		@SuppressWarnings("unused") // used by JSON serializer
		@Nullable
		public String getCode() {
			return this.code;
		}
	}
}
