package com.app.smartdrive.api.Exceptions;

public class EmployeesNotFoundException extends RuntimeException{
   
    public EmployeesNotFoundException(String message) {
        super(message);
  }
}
