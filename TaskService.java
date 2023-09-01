package com.asignment.app.service;

import com.asignment.app.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    Task updateTask(Long id, Task updatedTask);
    String deleteTask(Long id);
}
