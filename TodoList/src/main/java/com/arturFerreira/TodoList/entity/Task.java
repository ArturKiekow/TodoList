package com.arturFerreira.TodoList.entity;

import com.arturFerreira.TodoList.enums.Priority;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean finished;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    public Task(Long id, String title, String description, boolean finished, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.priority = priority;
    }
    public Task(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
