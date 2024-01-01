package com.app.smartdrive.api.Exceptions;

public class TasksNotCompletedException extends RuntimeException {

    public TasksNotCompletedException(String message) {
        super(message);
    }

    public TasksNotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
