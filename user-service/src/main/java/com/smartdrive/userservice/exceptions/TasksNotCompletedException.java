package com.smartdrive.userservice.exceptions;

public class TasksNotCompletedException extends RuntimeException {

    public TasksNotCompletedException(String message) {
        super(message);
    }

}
