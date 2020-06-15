package com.ibm.healthplanner.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomEntityResponseExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(Exception.class)  
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request)  
	{  
		//creating exception response structure  
		ExceptionResponse exceptionResponse= new ExceptionResponse(LocalDateTime.now(), 
							HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
							ex.getMessage(), request.getDescription(false));   
		   
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);  
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)  
	public final ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, 
							WebRequest request){  
		
		//creating exception response structure  
		ExceptionResponse exceptionResponse= new ExceptionResponse(LocalDateTime.now(), 
										HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
										ex.getMessage(), request.getDescription(false));  
		 
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);  
	} 
	
	@ExceptionHandler(ResourceExistsException.class)  
	public final ResponseEntity<Object> handleResourceExistsException(ResourceExistsException ex, 
							WebRequest request){  
		
		//creating exception response structure  
		ExceptionResponse exceptionResponse= new ExceptionResponse(LocalDateTime.now(), 
										HttpStatus.FOUND.value(), HttpStatus.FOUND.getReasonPhrase(),
										ex.getMessage(), request.getDescription(false));  
		 
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FOUND);  
	} 


    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    public final ResponseEntity<ExceptionResponse> handleBadRequestAllExceptions(
            RuntimeException ex, WebRequest request) {

        //ApplicationLogger.logger().error(ex.getMessage());

        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
