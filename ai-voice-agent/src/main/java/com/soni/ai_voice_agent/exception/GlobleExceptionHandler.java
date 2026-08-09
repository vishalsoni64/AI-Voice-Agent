package com.soni.ai_voice_agent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {
	
	
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<String> handleTaskNotFEntity(
			TaskNotFoundException exception){
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(exception.getMessage());
				
		
		

}
}