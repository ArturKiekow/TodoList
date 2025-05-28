package com.arturFerreira.TodoList.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TaskAlreadyFinishedException extends TodoListException {

    private String detail;

    public TaskAlreadyFinishedException(String message) {
        super(message);
        detail = message;
    }

    public TaskAlreadyFinishedException() {
        super("You cannot update a finished task");
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pd.setTitle("This task is already finished");
        pd.setDetail(detail);

        return pd;
    }
}
