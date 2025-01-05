package com.springboot.blog.ExceptionHandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
	String resourceName;
	String fieldName;
	long fieldValue;
	String fieldValue2;
	
	public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue2) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue2));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue2 = fieldValue2;	
		}
	
	
}
