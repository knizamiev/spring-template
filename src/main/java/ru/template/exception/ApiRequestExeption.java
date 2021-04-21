package ru.template.exception;

public class ApiRequestExeption extends RuntimeException{

	public ApiRequestExeption(String message) {
		super(message);
	}

	public ApiRequestExeption(String s, Throwable throwable) {
		super(s, throwable);
	}
}
