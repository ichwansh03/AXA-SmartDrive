package com.app.smartdrive.api.Exceptions;

public class AccountNonActiveException extends RuntimeException{
  public AccountNonActiveException(){
    super("Inactive Account!");
  }
}
