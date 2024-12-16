package com.arturFerreira.TodoList.enums;

public enum Priority {
    URGENT("Urgent"),
    IMPORTANT("Important"),
    NORMAL("Normal"),
    LOW("Low");

    private final String name;

    Priority(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
