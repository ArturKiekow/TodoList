package com.arturFerreira.TodoList.enums;

public enum Priority {
    LOW("Low"),
    NORMAL("Normal"),
    IMPORTANT("Important"),
    URGENT("Urgent");

    private final String name;

    Priority(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
