package com.arturFerreira.TodoList.controller;

import com.arturFerreira.TodoList.dto.CreateTaskDto;
import com.arturFerreira.TodoList.dto.TaskResponseDto;
import com.arturFerreira.TodoList.dto.UpdateTaskDto;
import com.arturFerreira.TodoList.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolist")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAll(@RequestParam(name = "orderBy", defaultValue = "desc") String orderBy){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAll(orderBy));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> create(@RequestBody @Valid CreateTaskDto taskDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(taskDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable("id") Long id, @RequestBody UpdateTaskDto taskDto){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(id, taskDto));
    }

    @PutMapping("/changeFinished/{id}")
    public ResponseEntity<TaskResponseDto> changeFinished(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.changeFinished(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("A tarefa foi deletada com sucesso");
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteFinishedTasks() {
        taskService.deleteAllFinished();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("As tarefas finalizadas foram deletadas com sucesso");
    }
}
