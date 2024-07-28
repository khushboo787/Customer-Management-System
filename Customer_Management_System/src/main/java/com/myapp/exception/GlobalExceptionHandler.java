package com.myapp.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {	//this is a single point to handle exception for whole application
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleException(CustomerNotFoundException ex, WebRequest request){
		//Create an object of ErrorDetails
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(details, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> handleException404(NoHandlerFoundException ex, WebRequest request){
		//Create an object of ErrorDetails
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(details, HttpStatus.BAD_REQUEST);
	}
	
	   @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<Map<String , Object>> handleUserNotFoundException(UserNotFoundException exception) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", exception.getMessage()));
	    }

	    @ExceptionHandler(UserRegistrationFailedException.class)
	    public ResponseEntity<Map<String , Object>> handleUserRegistrationFailedException(UserRegistrationFailedException exception) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", exception.getMessage()));
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String , Object>> handleException(Exception exception) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", exception.getMessage()));
	    }

	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<Map<String , Object>> handleRuntimeException(RuntimeException exception) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", exception.getMessage()));
	    }

	     

	    
}
