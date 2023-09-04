package com.asignment.app.service;

import com.asignment.app.dao.TaskRequest;
import com.asignment.app.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(String tokenUserName,TaskRequest task);
    Task getTaskById(String tokenUserName,Long id);
    List<Task> getAllTasks();
    Task updateTask(String tokenUserName, Long id, TaskRequest updatedTask);
    String deleteTask(String tokenUserName,Long id);

    List<Task> getTasksByUserId(Long uid);
}
