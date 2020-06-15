package com.ibm.healthplanner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND) 
public class ResourceExistsException extends RuntimeException {
	
	public ResourceExistsException(String message)   
	{  
	super(message);  
	} 

}
