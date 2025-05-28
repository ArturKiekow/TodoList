package com.arturFerreira.TodoList.service;

import com.arturFerreira.TodoList.dto.CreateTaskDto;
import com.arturFerreira.TodoList.dto.TaskResponseDto;
import com.arturFerreira.TodoList.dto.UpdateTaskDto;
import com.arturFerreira.TodoList.entity.Task;
import com.arturFerreira.TodoList.exceptions.PriorityNotFoundException;
import com.arturFerreira.TodoList.exceptions.TaskAlreadyFinishedException;
import com.arturFerreira.TodoList.exceptions.TaskNotFoundException;
import com.arturFerreira.TodoList.repository.PriorityRepository;
import com.arturFerreira.TodoList.repository.TaskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;


    public TaskService(TaskRepository taskRepository, PriorityRepository priorityRepository) {
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
    }

    public TaskResponseDto getById(Long id){
        var taskOp = taskRepository.findById(id);
        if (taskOp.isEmpty()){
            throw new TaskNotFoundException("Task with id=" + id + " not found!");
        }
        return TaskResponseDto.fromEntity(taskOp.get());
    }

    public List<TaskResponseDto> getAll(String orderBy){
        Sort sort = getSort(orderBy);
        return taskRepository.findAll(sort).stream()
                .map(TaskResponseDto::fromEntity).toList();
    }

    private static Sort getSort(String orderBy) {
        var direction = Sort.Direction.DESC;
        if (orderBy.equalsIgnoreCase("asc")){
            direction = Sort.Direction.ASC;
        }
        return Sort.by(direction, "priority.value");
    }

    public List<Task> getAllFinished(){
        return taskRepository.findByFinishedTrue();
    }

    public TaskResponseDto create(CreateTaskDto taskDto){
        var task = new Task();
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        var priority = priorityRepository.findByNameIgnoreCase(taskDto.priority())
                        .orElseThrow(() -> new PriorityNotFoundException("The priority is not correct or it does not exist"));
        task.setPriority(priority);
        return TaskResponseDto.fromEntity(taskRepository.save(task));
    }

    public TaskResponseDto update(Long id, UpdateTaskDto taskDto){
        var taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()){
            throw new TaskNotFoundException("Task with id=" + id + " not found!");
        }
        if (taskOptional.get().isFinished()){
            throw new TaskAlreadyFinishedException("You cannot update a finished task");
        }

        updateFields(taskDto, taskOptional.get());

        return TaskResponseDto.fromEntity(taskRepository.save(taskOptional.get()));
    }

    private void updateFields(UpdateTaskDto taskDto, Task task) {
        if (hasText(taskDto.title())){
            task.setTitle(taskDto.title());
        }
        if (hasText(taskDto.description())){
            task.setDescription(taskDto.description());
        }
        if (hasText(taskDto.priority())){
            var priority = priorityRepository.findByNameIgnoreCase(taskDto.priority())
                    .orElseThrow(() -> new PriorityNotFoundException("The priority is not correct or it does not exist"));
            task.setPriority(priority);
        }
    }

    public TaskResponseDto changeFinished(Long id){
        var taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()){
            throw new TaskNotFoundException("Task with id=" + id + " not found!");
        }

        var task = taskOptional.get();

        task.setFinished(!task.isFinished());

        return TaskResponseDto.fromEntity(taskRepository.save(task));
    }

    public void delete(Long id){
        var task = taskRepository.existsById(id);
        if (!task){
            throw new TaskNotFoundException("Task with id=" + id + " not found!");
        }
        taskRepository.deleteById(id);
    }

    public void deleteAllFinished(){
        var tasksFinished = getAllFinished();
        for (Task task : tasksFinished) {
            taskRepository.delete(task);
        }
    }
}
