package com.arturFerreira.TodoList.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TodoListException extends RuntimeException {
    public TodoListException(String message) {
        super(message);
    }

    public ProblemDetail toProblemDetail(){
        var pd = ProblemDetail.forStatus(500);

        pd.setTitle("Internal server error");
        pd.setDetail("An unexpected error occurred");

        return pd;
    }

}
