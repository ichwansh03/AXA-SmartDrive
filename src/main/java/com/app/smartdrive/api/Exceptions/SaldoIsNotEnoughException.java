package com.app.smartdrive.api.Exceptions;

public class SaldoIsNotEnoughException extends RuntimeException{
    public SaldoIsNotEnoughException(String message){
        super(message);
    }
    
}
