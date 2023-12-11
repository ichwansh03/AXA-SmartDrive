package com.smartdrive.masterservice.exceptions;

import org.springframework.http.HttpStatus;

public class TasksNotCompletedException extends RuntimeException {

    public TasksNotCompletedException(String message) {
        super(message);
    }

    public TasksNotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TasksNotCompletedException(Throwable cause) {
        super(cause);
    }

    protected TasksNotCompletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
