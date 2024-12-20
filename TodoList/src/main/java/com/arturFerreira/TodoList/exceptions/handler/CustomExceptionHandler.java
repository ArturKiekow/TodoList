package com.arturFerreira.TodoList.exceptions.handler;

import com.arturFerreira.TodoList.exceptions.ErrorMessage;
import com.arturFerreira.TodoList.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorMessage> taskNotFoundHandler(TaskNotFoundException exception, WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }
}
