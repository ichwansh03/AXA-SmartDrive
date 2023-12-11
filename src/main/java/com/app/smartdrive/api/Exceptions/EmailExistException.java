package com.app.smartdrive.api.Exceptions;

public class EmailExistException extends RuntimeException{
  public EmailExistException() {
    super("Email already exist!");
  }
}
