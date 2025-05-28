package com.arturFerreira.TodoList.exceptions.dto;

public record InvalidParamDto(
        String field,
        String reason
) {
}
