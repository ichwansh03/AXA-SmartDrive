package com.smartdrive.serviceorderservice.Exceptions;

public class SaldoIsNotEnoughException extends RuntimeException{
    public SaldoIsNotEnoughException(String message){
        super(message);
    }
    
}
