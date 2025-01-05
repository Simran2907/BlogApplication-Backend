package com.springboot.blog.ExceptionHandling;

public class ApiException extends RuntimeException{

	public ApiException(String message) {
		super(message);
	}
	
	public ApiException() {
		super();
	}
}
