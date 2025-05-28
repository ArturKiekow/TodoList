package com.arturFerreira.TodoList.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PriorityNotFoundException extends TodoListException {

    private String detail;

    public PriorityNotFoundException(String message) {
        super(message);
        detail = message;
    }

    public PriorityNotFoundException(){
        super("Priority not found");
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pd.setTitle("Priority not found");
        pd.setDetail(detail);

        return pd;
    }
}
