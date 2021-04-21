package ru.template.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
	private final String message;
	private final Throwable throwable;

	public ApiException(String message,
	                    Throwable throwable) {
		this.message = message;
		this.throwable = throwable;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}
}
