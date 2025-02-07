package com.arturFerreira.TodoList.exceptions;

public class TaskAlreadyFinishedException extends RuntimeException {
    public TaskAlreadyFinishedException(String message) {
        super(message);
    }

    public TaskAlreadyFinishedException() {
        super("You cannot update a finished task");
    }
}
