package com.asignment.app.controller;

import com.asignment.app.dao.Response;
import com.asignment.app.dao.TaskRequest;
import com.asignment.app.entity.Task;
import com.asignment.app.jwt.JWTController;
import com.asignment.app.jwt.JwtUtil;
import com.asignment.app.service.TaskService;
import com.asignment.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @PostMapping("/add-task")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskRequest task, @RequestHeader("Authorization") String cToken) {
        String tokenUserName = jwtUtil.extractUsername(JWTController.getCurrentToken());
        if (task == null) {
            Response response = new Response();
            response.setMsg("Please add task details");
            return ResponseEntity.badRequest().body(response);
        }else if(tokenUserName.isEmpty()){
            Response response = new Response();
            response.setMsg("Please login with appropriate username and password...!....");
            return ResponseEntity.badRequest().body(response);
        }
       else {
            Task createdTask = taskService.createTask(tokenUserName, task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        }
    }

    // get task by task id
    @GetMapping("/get-task/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Null fields not available");
        }
        String tokenUserName = jwtUtil.extractUsername(JWTController.getCurrentToken());
        Task task = taskService.getTaskById(tokenUserName,id);
        if (task == null) {
            Response response = new Response();
            response.setMsg("Action Denied. you won't belong to this task");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/get-tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/update-task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest task) {
        String tokenUserName = jwtUtil.extractUsername(JWTController.getCurrentToken());
        if (task == null) {
            Response response = new Response();
            response.setMsg("Please add task details");
            return ResponseEntity.badRequest().body(response);
        }else if(tokenUserName.isEmpty()){
            Response response = new Response();
            response.setMsg("Please login with appropriate username and password...!....");
            return ResponseEntity.badRequest().body(response);
        }else {
            Task updatedTask = taskService.updateTask(tokenUserName, id, task);
            if (updatedTask == null) {
                Response r = new Response("Action Denied. you won't belong to this task");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(r);
            }
            return ResponseEntity.ok(updatedTask);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTask(@PathVariable Long id) {
        String tokenUserName = jwtUtil.extractUsername(JWTController.getCurrentToken());
        String result = "";
        result = taskService.deleteTask(tokenUserName,id);
        if (result == null) {
            Response response = new Response();
            response.setMsg("Action Denied. you won't belong to this task");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        Response response = new Response();
        response.setMsg(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-task-by-user/{uid}")
    public ResponseEntity<?> getTasksByUser(@PathVariable Long uid) {
        if (uid == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Null fields not allowed");
        }
        // Validate the token and get the username from it
        String tokenUserName = jwtUtil.extractUsername(JWTController.getCurrentToken());
        // Check if the token user matches the requested user.
        if (!userService.validateUser(tokenUserName, uid)) {
            Response response = new Response();
            response.setMsg("Access denied. You can only access your own tasks.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        // Get tasks for the user
        List<Task> tasks = taskService.getTasksByUserId(uid);
        if (tasks.isEmpty()) {
            Response response = new Response();
            response.setMsg("Hello " + tokenUserName + ", you don't have any tasks added. Please add new tasks.");
            // Return 200 OK with a message
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(tasks);
    }
}

