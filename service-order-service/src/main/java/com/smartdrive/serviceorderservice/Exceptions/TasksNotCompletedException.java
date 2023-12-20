package com.smartdrive.serviceorderservice.Exceptions;

public class TasksNotCompletedException extends RuntimeException {

    public TasksNotCompletedException(String message) {
        super(message);
    }

}
