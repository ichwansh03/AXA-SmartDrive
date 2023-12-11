package com.app.smartdrive.api.Exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


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
