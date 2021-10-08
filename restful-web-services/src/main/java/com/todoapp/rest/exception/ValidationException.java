package com.todoapp.rest.exception;

public class ValidationException extends Exception{
    public ValidationException(String errorMessage) {
        super(errorMessage);
    }

}
