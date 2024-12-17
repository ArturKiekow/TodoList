package com.arturFerreira.TodoList.service;

import com.arturFerreira.TodoList.entity.Task;
import com.arturFerreira.TodoList.repository.TaskRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getById(Long id){
        return taskRepository.getById(id);
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public Task create(Task task){
        return taskRepository.save(task);
    }

    public Task update(Long id, Task updateTask){
        var savedTask = taskRepository.findById(id);
        if (savedTask.isEmpty()){
            return null;
        }
        Task task = savedTask.get();
        task.setTitle(updateTask.getTitle());
        task.setDescription(updateTask.getDescription());
        task.setFinished(updateTask.isFinished());
        task.setPriority(updateTask.getPriority());
        return taskRepository.save(task);
    }

    public void delete(Long id){
        var task = taskRepository.findById(id);
        if (task.isEmpty()){
            //throw new Exception();
        }
        taskRepository.delete(task.get());
    }
}
