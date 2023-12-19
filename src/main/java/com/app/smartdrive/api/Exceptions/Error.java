package com.app.smartdrive.api.Exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {
  @Override
  public String toString() {
    return "Error{" +
            "errorCode='" + errorCode + '\'' +
            ", message='" + message + '\'' +
            ", status=" + status +
            ", url='" + url + '\'' +
            ", reqMethod='" + reqMethod + '\'' +
            ", timestamp=" + timestamp +
            '}';
  }

  private static final long serialVersionUID = 1L;
  /** Application error code, which is different from HTTP error code. */
  private String errorCode;

  /** Short, human-readable summary of the problem. */
  private String message;

  /** HTTP status code for this occurrence of the problem, set by the origin server. */
  private Integer status;

  /** Url of request that produced the error. */
  private String url = "Not available";

  /** Method of request that produced the error. */
  private String reqMethod = "Not available";

  /** Timestamp */
  private LocalDateTime timestamp;

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getUrl() {
    return url;
  }

  public Error setUrl(String url) {
    if (Strings.isNotBlank(url)) {
      this.url = url;
    }
    return this;
  }

  public String getReqMethod() {
    return reqMethod;
  }

  public Error setReqMethod(String method) {
    if (Strings.isNotBlank(method)) {
      this.reqMethod = method;
    }
    return this;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public Error setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }
}