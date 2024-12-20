package com.arturFerreira.TodoList.controller;

import com.arturFerreira.TodoList.dto.TaskRequestDTO;
import com.arturFerreira.TodoList.entity.Task;
import com.arturFerreira.TodoList.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolist")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAll());
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid TaskRequestDTO taskDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(taskDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable("id") Long id, @RequestBody @Valid TaskRequestDTO taskDto){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(id, taskDto));
    }

    @PutMapping("/changeFinished/{id}")
    public ResponseEntity<Task> changeFinished(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.changeFinished(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("A tarefa foi deletada com sucesso");
    }
}
