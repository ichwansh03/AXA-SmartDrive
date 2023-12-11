package com.app.smartdrive.api.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter @Setter
public class ErrorMessage {
  private int statusCode;
  private Date timestamp;
  private String message;
  private String description;

}