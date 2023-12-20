package com.smartdrive.serviceorderservice.Exceptions;

public class ErrorUtils {

  private ErrorUtils() {}

  /**
   * Creates and return an error object
   *
   * @param errMsgKey
   * @param errorCode
   * @param httpStatusCode
   * @return error
   */
  public static java.lang.Error createError(
          final String errMsgKey, final String errorCode, final Integer httpStatusCode) {
    java.lang.Error error = new java.lang.Error();
    error.setMessage(errMsgKey);
    error.setErrorCode(errorCode);
    error.setStatus(httpStatusCode);
    return error;
  }
}
