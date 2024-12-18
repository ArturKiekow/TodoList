package com.arturFerreira.TodoList.dto;

import com.arturFerreira.TodoList.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestDTO(

        @NotBlank @NotNull
        String title,
        @NotBlank @NotNull
        String description,
        @NotNull
        Priority priority
)
{
}
