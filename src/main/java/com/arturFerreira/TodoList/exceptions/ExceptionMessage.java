package com.arturFerreira.TodoList.exceptions;

import org.springframework.http.HttpStatus;

public class ExceptionMessage {
    private HttpStatus status;
    private String message;
    private String description;

    public ExceptionMessage(HttpStatus status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
