package com.arturFerreira.TodoList.service;

import com.arturFerreira.TodoList.dto.TaskRequestDTO;
import com.arturFerreira.TodoList.entity.Task;
import com.arturFerreira.TodoList.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getById(Long id){
        var task = taskRepository.findById(id);
        if (task.isEmpty()){
            return null;
        }
        return task.get();
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public Task create(TaskRequestDTO taskDto){
        var task = new Task(taskDto);
        return taskRepository.save(task);
    }

    public Task update(Long id, TaskRequestDTO taskDto){
        var taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()){
            return null;
        }
        var task = taskOptional.get();
        BeanUtils.copyProperties(task, taskDto);
        return taskRepository.save(task);
    }

    public Task changeFinished(Long id){
        var taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()){
            return null;
        }
        var task = taskOptional.get();
        if (task.isFinished()){
            task.setFinished(false);
        } else {
            task.setFinished(true);
        }
        taskRepository.save(task);
        return task;
    }

    public void delete(Long id){
        var task = taskRepository.findById(id);
        if (task.isEmpty()){
            //throw new Exception();
        }
        taskRepository.delete(task.get());
    }
}
