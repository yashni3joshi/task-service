package com.asignment.app.controller;

import com.asignment.app.dao.Response;
import com.asignment.app.entity.Task;
import com.asignment.app.jwt.JWTController;
import com.asignment.app.service.TaskService;

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

    @PostMapping("/add-task")
    public ResponseEntity<?> createTask(@RequestBody @Valid Task task,@RequestHeader("Authorization") String cToken) {
//        if(JWTController.getCurrentToken()== null){
//            Response response = new Response();
//            response.setMsg("Authentication failed, Login First");
//            return ResponseEntity.badRequest().body(response);
//        }
      //  if(cToken.substring(7).matches(JWTController.getCurrentToken())) {
            if (task == null) {
                Response response = new Response();
                response.setMsg("Please add task details");
                return ResponseEntity.badRequest().body(response);
            }
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        }
//        else {
//            Response response = new Response();
//            response.setMsg("Authentication failed, token not valid");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
   // }

    @GetMapping("/get-task/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        if(id == null){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Null fields not available");
        }
        Task task = taskService.getTaskById(id);
        if(task == null) {
            Response response = new Response();
            response.setMsg("No user found with this id");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/get-tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/update-task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody @Valid Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        if(updatedTask == null){
            Response r = new Response("No User Found with this id");
            return ResponseEntity.badRequest().body(r);
        }
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteTask(@PathVariable Long id) {
        String result ="";
        result = taskService.deleteTask(id);
        if(result == null) {
            Response response = new Response();
            response.setMsg("No user found with this id");
            return ResponseEntity.badRequest().body(response);
        }
        Response response = new Response();
        response.setMsg(result);
        return ResponseEntity.ok(response);
    }
}

