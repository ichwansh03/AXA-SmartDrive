package com.smartdrive.serviceorderservice.Exceptions;

public class UserPhoneExistException extends RuntimeException{
  public UserPhoneExistException(String phoneNumber) {
    super(phoneNumber+" already exist!");
  }
}
