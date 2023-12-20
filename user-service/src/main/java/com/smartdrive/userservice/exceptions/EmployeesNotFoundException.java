package com.smartdrive.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeesNotFoundException extends RuntimeException{
   
    public EmployeesNotFoundException(String message) {
        super(message);
  }
}
