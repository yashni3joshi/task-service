package com.asignment.app.service.impl;

import com.asignment.app.dao.TaskRequest;
import com.asignment.app.entity.Task;
import com.asignment.app.entity.User;
import com.asignment.app.jwt.JwtUtil;
import com.asignment.app.repository.TaskRepository;
import com.asignment.app.repository.UserRepository;
import com.asignment.app.service.TaskService;
import com.asignment.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Override
    public Task createTask(String tokenUserName,TaskRequest taskRequest) {

        Task tasks = new Task();
        tasks.setCreatedAt(LocalDateTime.now());

        User user =  userService.findByUserName(tokenUserName);
        // Set the User in the Task
        tasks.setUser(user);
        tasks.setTitle(taskRequest.getTitle());
        tasks.setDescription(taskRequest.getDescription());
        tasks.setCompleted(tasks.isCompleted());
        return taskRepository.save(tasks);
    }
    @Override
    public Task getTaskById(String tokenUserName,Long id) {
       Task task = taskRepository.findTaskById(id);
         if(task.getUser().getUName().matches(tokenUserName)){
             return task;
        }
         return null;
    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Override
    public Task updateTask(String tokenUserName,Long id, TaskRequest updatedTask) {
        User user =  userService.findByUserName(tokenUserName);
        if(user.getUid().equals(id)){
            Task task =  taskRepository.findTaskById(id);
            if(task == null){
                return null;
            }
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        }else
            return null;
    }
    @Override
    public String deleteTask(String tokenUserName,Long id) {
        // finding user from token username
        User users =  userService.findByUserName(tokenUserName);
        // finding user from task id data
            Task task = taskRepository.findById(id).orElse(null);
            if (task == null) {
                return null;
            }
            if(task.getUser().getUid().equals(users.getUid())){

            // Step 2: Remove the Task from the associated User's tasks
            User user = task.getUser(); // Assuming you have a getUser() method
            user.getTasks().remove(task);

            // Step 3: Delete the Task
            taskRepository.delete(task);

            return "Deleted data successfully";
        }
        return null;
    }
    @Override
    public List<Task> getTasksByUserId(Long uid) {
        return taskRepository.findByUserUid(uid);
    }


}
