package de.nieder.kick.common;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class ServiceException extends BaseException {
	public ServiceException(@NonNull final String service, @NonNull final String message) {
		super("Error in " + service + ": " + message);
	}

	@Override
	@NonNull
	public HttpStatus getResponseStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	@NonNull
	public String getErrorCode() {
		return "item-exists";
	}

	@Override
	@NonNull
	public String getErrorMessage() {
		return "Item already exists";
	}

	@Override
	@NonNull
	public String getErrorDescription() {
		return this.getMessage();
	}
}
