package com.todoapp.rest.controller;

import com.todoapp.rest.exception.PageOutOfRangeException;
import com.todoapp.rest.exception.TaskNotFoundException;
import com.todoapp.rest.exception.ValidationException;
import com.todoapp.rest.model.ErrorResponseBody;
import com.todoapp.rest.model.Task;
import com.todoapp.rest.model.TaskListRange;
import com.todoapp.rest.service.TodoHardcodedService;
import com.todoapp.rest.validator.TodoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TodoHardcodedService todoService;
    private  TodoValidator validator = new TodoValidator();
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private String FAILURE_STATUS = "fails";

    @GetMapping("/users/{username}/todos")
    public ResponseEntity<List<?>> getAllTodos(@PathVariable String username){
        logger.info("Got get request for all tasks for user :" + username + " using API : users/{username}/todos");
        try {
            List<Task> responseList = todoService.findAll();
            if (responseList.isEmpty())
                    throw new TaskNotFoundException("No task found");
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }
        catch (Exception e){
            List<Object> responseList = new ArrayList<Object>();
            responseList.add(new ErrorResponseBody(FAILURE_STATUS, e.getMessage()));
            logger.error("Got exception : " + e.getMessage() + " when fetching all tasks");
            return new ResponseEntity<>( responseList, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users/{username}/todos/page/{pageNumber}")
    public ResponseEntity<?> getTodosByPage(@PathVariable String username, @PathVariable Integer pageNumber,
                                            @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                            HttpServletRequest req){
        logger.info("Got get request for tasks for user : " + username + " with page number : " + pageNumber + " using API : users/{username}/todos/{pageNumber}");
        try {
            if(pageNumber < 1 || pageSize < 1)
                throw new PageOutOfRangeException("Page number and page size should be more than 1");
            List<Task> taskList = todoService.findAll();
            if (taskList.isEmpty())
                throw new TaskNotFoundException("No task found");
            int totalPages = (int)Math.ceil(taskList.size()/pageSize);
            if(totalPages < 1)
                throw new TaskNotFoundException("No pages found");
            int index = (pageNumber-1)*pageSize;
            int maxIndex = pageNumber*pageSize;
            List<Task> tasksByPage = new ArrayList<Task>();
            try {
                while ((index < maxIndex) && (maxIndex <= taskList.size())) {
                    tasksByPage.add(taskList.get(index));
                    index++;
                }
            }catch (ArrayIndexOutOfBoundsException ae){
                throw new PageOutOfRangeException("Task number is bigger than the total tasks present.");
            }
            String prevLink = null;
            String nextLink = null;
            if(pageNumber > 1)
                prevLink = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api/v1/users/" + username + "/todos/page/" + (pageNumber - 1) ).toUriString();
            if(maxIndex < taskList.size())
                nextLink = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api/v1/users/" + username + "/todos/page/" + (pageNumber + 1)).toUriString();

            TaskListRange responseList = new TaskListRange(prevLink, nextLink, tasksByPage, totalPages);

            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }
        catch (Exception e){
            List<Object> responseList = new ArrayList<Object>();
            responseList.add(new ErrorResponseBody(FAILURE_STATUS, e.getMessage()));
            logger.error("Got exception : " + e.getMessage() + " when fetching tasks with page number : " + pageNumber);
            return new ResponseEntity<>( responseList, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users/{username}/todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable String username, @PathVariable long id){
        logger.info("Got get request for the task for user :" + username + " with id: "+ id + "using API : users/{username}/todos/{id}");
        try {
            Task task = todoService.findById(id);
            if( task == null)
                throw new TaskNotFoundException("No task found with id : " + id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("Got exception : " + e.getMessage() + " when fetching task with id.");
            return new ResponseEntity<>( new ErrorResponseBody(FAILURE_STATUS, e.getMessage()), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users/{username}/todos/filter")
    public ResponseEntity<List<?>> getTodoByFilter(@PathVariable String username, @RequestParam(value = "task", required = false) String name,
                                                 @RequestParam(value = "desc", required = false) String description){
        logger.info("Got get request for the task for user :" + username + " with name: "+ name + " and description " + description +" using API : users/{username}/todos/filter");
        try {
            if(name==null && description== null)
                throw new ValidationException("Both name and description is null for filter");
            List<Task> responseList = todoService.findByFilter(name, description);
            if (responseList.isEmpty())
                throw new TaskNotFoundException("No task found with name :" + name + " and description : " + description);
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }
        catch (ValidationException e){
            List<Object> responseList = new ArrayList<Object>();
            responseList.add(new ErrorResponseBody(FAILURE_STATUS, e.getMessage()));
            logger.error("Got exception : " + e.getMessage() + " when fetching tasks with filter");
            return new ResponseEntity<>( responseList, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            List<Object> responseList = new ArrayList<Object>();
            responseList.add(new ErrorResponseBody(FAILURE_STATUS, e.getMessage()));
            logger.error("Got exception : " + e.getMessage() + " when fetching tasks with filter");
            return new ResponseEntity<>( responseList, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/users/{username}/todos")
    public ResponseEntity<?> addTodo(@PathVariable String username, @RequestBody Task task) throws Exception {
        logger.info("Got request to create new task for user :" + username + " with task name : "+ task.getTask() + "using API : users/{username}/todos");

        try{
            if(validator.validateNewTask(task)) {
                task.setUsername((username));
                Task createdTask = todoService.save(task);
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTask.getId()).toUri();
                //Passing the new URL back
                return ResponseEntity.created(uri).build();
            }
            else{
                throw new ValidationException("validation failed for new task details");
            }
        }
        catch (Exception e){
            logger.error("Got exception : " + e.getMessage() + " when creating new task.");
            return new ResponseEntity<>( new ErrorResponseBody(FAILURE_STATUS, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/{username}/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Task task){
        logger.info("Got request to update existing task for user :" + username + " with task id : "+ task.getId() + "using API : users/{username}/todos/{id}");
        try {
            if(validator.validateNewTask(task)) {
                Task taskUpdated = todoService.save(task);
                return new ResponseEntity<Task>(taskUpdated, HttpStatus.OK);
            }
            else{
                throw new ValidationException("validation failed for new task details");
            }
        }
        catch (Exception e){
            logger.error("Got exception : " + e.getMessage() + " when updating existing task.");
            return new ResponseEntity<>( new ErrorResponseBody(FAILURE_STATUS, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/users/{username}/todos/{id}")
    public ResponseEntity<?> deleteTodo (@PathVariable String username, @PathVariable long id){
        logger.info("Got request to delete existing task for user :" + username + " with task id : "+ id + "using API : users/{username}/todos/{id}");
        try {
            todoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e){
            logger.error("Got exception : " + e.getMessage() + " when deleting existing task.");
            return new ResponseEntity<>( new ErrorResponseBody(FAILURE_STATUS, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
