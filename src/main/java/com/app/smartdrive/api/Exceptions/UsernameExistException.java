package com.app.smartdrive.api.Exceptions;

public class UsernameExistException extends RuntimeException{
  public UsernameExistException() {
    super("Username already exist!");
  }
}
