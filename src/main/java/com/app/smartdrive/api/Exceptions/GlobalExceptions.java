package com.app.smartdrive.api.Exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptions {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptions.class);

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Error> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        Error error = ErrorUtils.createError(
                ex.getMessage(),
                ex.getLocalizedMessage(),HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleAllExceptionMethod(MethodArgumentNotValidException ex, WebRequest requset) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().getAllErrors());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleErrorSqlException(SQLException sql){

        return ResponseEntity.internalServerError().body(sql.getMessage());
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<?> handleErrorInvalidDataAccess(InvalidDataAccessResourceUsageException ex, HttpServletRequest request){
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ex.getLocalizedMessage(),
                        HttpStatus.BAD_REQUEST.value())
                .setUrl(request.getRequestURL().toString())
                .setReqMethod(request.getMethod())
                .setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    

    @ExceptionHandler(BindException.class)
    public final ResponseEntity<?> handleBindException(BindException ex) {
        Error error = ErrorUtils.createError(
                        ex.getMessage(),
                        ex.getLocalizedMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setTimestamp(LocalDateTime.now());

    //     /*
    //      * Map<String, String> errors = new HashMap<>();
    //      * ex.getFieldErrors().forEach(fieldError -> {
    //      * String fieldName = fieldError.getField();
    //      * String errorMessage = fieldError.getDefaultMessage();
    //      * errors.put(fieldName, errorMessage);
    //      * });
    //      */

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


   @ExceptionHandler(ValidasiRequestException.class)
   public ResponseEntity<?> handleValidasi(ValidasiRequestException e){
       return ResponseEntity.status(400).body(e.toString()+ "ERROR VALIDASI MASUK DISINI");
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
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException ex){
        Error error = ErrorUtils.createError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TasksNotCompletedException.class)
    public ResponseEntity<?> httpRequestTasksNotCompletedException(TasksNotCompletedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<?> methodArgumentConversionNotSupportedException(
            MethodArgumentConversionNotSupportedException ex) {

        Error error = ErrorUtils.createError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaldoIsNotEnoughException.class)
    public ResponseEntity<?> saldoIsNotEnoughException(SaldoIsNotEnoughException ex){
        Error error = ErrorUtils.createError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<?> userNameExist(UsernameExistException ex){
        Error error = ErrorUtils.createError(
                ex.getMessage(),ex.getLocalizedMessage(),HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<?> emailExist(EmailExistException ex){
        Error error = ErrorUtils.createError(
                ex.getMessage(),ex.getLocalizedMessage(),HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserPhoneExistException.class)
    public ResponseEntity<?> emailExist(UserPhoneExistException ex){
        Error error = ErrorUtils.createError(
                ex.getMessage(),ex.getLocalizedMessage(),HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(TypeTransaksiNotFoundException.class)
    public ResponseEntity<?> typeTransaksiNotFound(TypeTransaksiNotFoundException ex){
        Error error = ErrorUtils.createError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        Error error = ErrorUtils.createError(
                ex.getMessage(),ex.getLocalizedMessage(),HttpStatus.FORBIDDEN.value()
        );
        return new ResponseEntity<Object>(
                error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> jwtExpired(ExpiredJwtException ex){
        Error error = ErrorUtils.createError("Token expired, you can refresh with refreshtoken in /auth/refreshtoken",
                ex.getLocalizedMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<?> handleAuthenticationException(Exception ex) {

        Error re = ErrorUtils.createError("Authentication failed at controller advice",
                ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}

