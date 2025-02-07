package com.arturFerreira.TodoList.exceptions;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(){
        super("Task Not Found!");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

}
