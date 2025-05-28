package com.arturFerreira.TodoList.exceptions.handler;

import com.arturFerreira.TodoList.exceptions.TaskAlreadyFinishedException;
import com.arturFerreira.TodoList.exceptions.TaskNotFoundException;
import com.arturFerreira.TodoList.exceptions.TodoListException;
import com.arturFerreira.TodoList.exceptions.dto.InvalidParamDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(TodoListException.class)
    public ProblemDetail HandleTodoListException(TodoListException e){
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail HandleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        var invalidParams = e.getFieldErrors()
                .stream()
                .map(fe -> new InvalidParamDto(fe.getField(), fe.getDefaultMessage()))
                .toList();

        var pd = ProblemDetail.forStatus(400);
        pd.setTitle("Invalid request parameters");
        pd.setDetail("There is invalid fiels on the request");
        pd.setProperty("Invalid-Params", invalidParams);

        return pd;
    }


}
