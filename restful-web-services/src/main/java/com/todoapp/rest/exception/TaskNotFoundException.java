package com.todoapp.rest.exception;

public class TaskNotFoundException extends Exception{
    public TaskNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
