package com.arturFerreira.TodoList.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaskDto(

        @NotBlank @NotNull
        String title,
        @NotBlank @NotNull
        String description,
        @NotNull
        String priority
)
{
}
