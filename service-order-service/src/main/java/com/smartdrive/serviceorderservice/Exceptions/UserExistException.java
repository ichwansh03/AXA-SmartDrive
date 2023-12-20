package com.smartdrive.serviceorderservice.Exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}

