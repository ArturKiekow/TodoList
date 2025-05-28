package com.arturFerreira.TodoList.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaskDto(

        @NotBlank(message = "{title.not.blank}")
        String title,
        @NotBlank(message = "{description.not.blank}")
        String description,
        @NotBlank(message = "{priority.not.blank}")
        String priority
)
{
}
