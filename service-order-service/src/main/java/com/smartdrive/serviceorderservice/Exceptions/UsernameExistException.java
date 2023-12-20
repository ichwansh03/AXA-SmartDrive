package com.smartdrive.serviceorderservice.Exceptions;

public class UsernameExistException extends RuntimeException{
  public UsernameExistException() {
    super("Username already exist!");
  }
}
