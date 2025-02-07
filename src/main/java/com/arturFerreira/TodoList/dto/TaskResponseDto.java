package com.arturFerreira.TodoList.dto;

import com.arturFerreira.TodoList.entity.Task;

public record TaskResponseDto(
        Long taskId,
        String title,
        String description,
        boolean finished,
        String priority
) {

    public static TaskResponseDto fromEntity(Task task){
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isFinished(),
                task.getPriority().getName()
        );
    }

}
