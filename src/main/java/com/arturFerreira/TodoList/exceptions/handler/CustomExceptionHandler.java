package com.arturFerreira.TodoList.exceptions.handler;

import com.arturFerreira.TodoList.exceptions.ExceptionMessage;
import com.arturFerreira.TodoList.exceptions.TaskAlreadyFinishedException;
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
    public ResponseEntity<ExceptionMessage> taskNotFoundHandler(TaskNotFoundException exception, WebRequest webRequest){
        ExceptionMessage exceptionMessage = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(exceptionMessage.getStatus()).body(exceptionMessage);
    }

    @ExceptionHandler(TaskAlreadyFinishedException.class)
    public ResponseEntity<ExceptionMessage> taskAlreadyFinishedHandler(TaskAlreadyFinishedException exception, WebRequest webRequest){
        ExceptionMessage exceptionMessage = new ExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(exceptionMessage.getStatus()).body(exceptionMessage);
    }
}
