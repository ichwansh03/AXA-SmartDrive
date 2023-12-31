package com.app.smartdrive.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtExpiredException extends RuntimeException{
  public JwtExpiredException(String message){
    super(message);
  }
}
