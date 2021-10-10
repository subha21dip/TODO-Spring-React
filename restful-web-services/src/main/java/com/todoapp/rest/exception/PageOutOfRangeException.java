package com.todoapp.rest.exception;

public class PageOutOfRangeException extends Exception{
    public PageOutOfRangeException(String errorMessage) {
        super(errorMessage);
    }

}
