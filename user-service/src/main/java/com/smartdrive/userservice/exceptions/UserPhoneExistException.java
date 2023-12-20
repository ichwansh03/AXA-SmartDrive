package com.smartdrive.userservice.exceptions;

public class UserPhoneExistException extends RuntimeException{
  public UserPhoneExistException(String phoneNumber) {
    super(phoneNumber+" already exist!");
  }
}
