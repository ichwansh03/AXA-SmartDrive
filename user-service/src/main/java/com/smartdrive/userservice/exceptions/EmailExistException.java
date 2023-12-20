package com.smartdrive.userservice.exceptions;

public class EmailExistException extends RuntimeException{
  public EmailExistException() {
    super("Email already exist!");
  }
}
