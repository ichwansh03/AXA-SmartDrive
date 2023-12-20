package com.smartdrive.userservice.exceptions;

public class UsernameExistException extends RuntimeException{
  public UsernameExistException() {
    super("Username already exist!");
  }
}
