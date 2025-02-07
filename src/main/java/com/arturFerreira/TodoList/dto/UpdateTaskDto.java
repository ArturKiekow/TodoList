package com.arturFerreira.TodoList.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskDto(
        String title,
        String description,
        String priority
) {
}
