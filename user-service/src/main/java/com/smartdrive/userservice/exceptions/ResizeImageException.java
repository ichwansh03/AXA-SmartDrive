package com.smartdrive.userservice.exceptions;

public class ResizeImageException extends RuntimeException {

    public ResizeImageException() {
    }

    public ResizeImageException(String message) {
        super(message);
    }

    public ResizeImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResizeImageException(Throwable cause) {
        super(cause);
    }

    public ResizeImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
