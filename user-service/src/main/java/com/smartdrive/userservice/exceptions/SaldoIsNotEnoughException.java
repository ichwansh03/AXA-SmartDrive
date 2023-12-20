package com.smartdrive.userservice.exceptions;

public class SaldoIsNotEnoughException extends RuntimeException{
    public SaldoIsNotEnoughException(String message){
        super(message);
    }
    
}
