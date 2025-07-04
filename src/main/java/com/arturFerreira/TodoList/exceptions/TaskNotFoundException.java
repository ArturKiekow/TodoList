package com.arturFerreira.TodoList.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TaskNotFoundException extends TodoListException {

    private String detail;

    public TaskNotFoundException(){
        super("Task Not Found!");
    }

    public TaskNotFoundException(String message) {
        super(message);
        detail = message;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Task not found");
        pd.setDetail(detail);
        return pd;
    }
}
