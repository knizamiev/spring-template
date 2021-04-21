package ru.template.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
	@ExceptionHandler(value = {ApiRequestExeption.class})
	public ResponseEntity<Object> handleApiRequestException(ApiRequestExeption e){
		ApiException apiException = new ApiException(
				e.getMessage(),
				e
		);
		return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
	}
}
