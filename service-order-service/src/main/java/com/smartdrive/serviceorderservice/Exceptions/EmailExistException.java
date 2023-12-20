package com.smartdrive.serviceorderservice.Exceptions;

public class EmailExistException extends RuntimeException{
  public EmailExistException() {
    super("Email already exist!");
  }
}
