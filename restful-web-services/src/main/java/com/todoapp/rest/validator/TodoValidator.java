package com.todoapp.rest.validator;

import com.todoapp.rest.controller.TaskController;
import com.todoapp.rest.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TodoValidator {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public boolean validateTaskName(String taskName){
        if(taskName != null && taskName.length() >= 2 && taskName.length() <= 20) {
            return true;
        }
        else {
            logger.debug("Task name is too long or too short.");
            return false;
        }
    }
    public boolean validateTaskDescription(String description){
        if( description == null) //not mandatory
            return true;
        else if(description != null && description.length() <= 200) // if provided then length should not be more than 200
            return true;
        else {
            logger.debug("Task description is too long.");
            return false;
        }
    }

    public boolean validateNewTask(Task task){
        if(validateTaskName(task.getTask()) && validateTaskDescription(task.getDescription()))
            return true;
        else
            return false;
    }


}
