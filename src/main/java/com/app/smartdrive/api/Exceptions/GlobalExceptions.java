package com.app.smartdrive.api.Exceptions;

import java.time.LocalDateTime;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

@RestControllerAdvice
public class GlobalExceptions {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptions.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleAllExceptionMethod(MethodArgumentNotValidException ex, WebRequest requset) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().getAllErrors());
    }

    @ExceptionHandler(BindException.class)
    public final ResponseEntity<?> handleBindException(BindException ex) {
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ex.getLocalizedMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setTimestamp(LocalDateTime.now());

        /*
         * Map<String, String> errors = new HashMap<>();
         * ex.getFieldErrors().forEach(fieldError -> {
         * String fieldName = fieldError.getField();
         * String errorMessage = fieldError.getDefaultMessage();
         * errors.put(fieldName, errorMessage);
         * });
         */

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(
            HttpServletRequest request, Exception ex, Locale locale) {

        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ex.getLocalizedMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setUrl(request.getRequestURL().toString())
                .setReqMethod(request.getMethod())
                .setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<?> userExistException(UserExistException ex) {

        Error error = ErrorUtils.createError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFoundException(EntityNotFoundException ex){
        Error error = ErrorUtils.createError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> methodArgumentConversionNotSupportedException(
            MethodArgumentConversionNotSupportedException ex) {

        Error error = ErrorUtils.createError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
