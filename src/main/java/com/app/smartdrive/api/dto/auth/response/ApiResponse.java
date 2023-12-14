package com.app.smartdrive.api.dto.auth.response;

import lombok.Data;

@Data
public class ApiResponse {
  private int status;
  private String message;
  private Object result;

  public ApiResponse(int status, String message, Object result){
    this.status = status;
    this.message = message;
    this.result = result;
  }

  public ApiResponse(int status, String message){
    this.status = status;
    this.message = message;
  }

  @Override
  public String toString() {
    return "ApiResponse{" +
            "status=" + status +
            ", message='" + message + '\'' +
            ", result=" + result +
            '}';
  }
}
