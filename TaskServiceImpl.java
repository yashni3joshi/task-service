package com.asignment.app.service.impl;

import com.asignment.app.entity.Task;
import com.asignment.app.repository.TaskRepository;
import com.asignment.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElse(null);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = getTaskById(id);
        if(task == null){
            return null;
        }
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());
        return taskRepository.save(task);
    }

    public String deleteTask(Long id) {
        Task task = getTaskById(id);
        if(task == null){
            return null;
        }
        taskRepository.delete(task);
        return "Deleted data successfully";
    }
}
