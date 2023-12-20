package com.smartdrive.userservice.exceptions;

import lombok.Data;


@Data
public class ValidasiRequestException extends RuntimeException{

    private String pesan;
    private int statusCode;

    public ValidasiRequestException(String pesan, int statusCode){
        this.pesan = pesan;
        this.statusCode = statusCode;
    }

    public ValidasiRequestException(int statusCode){
        this.statusCode = statusCode;
    }


    
}
