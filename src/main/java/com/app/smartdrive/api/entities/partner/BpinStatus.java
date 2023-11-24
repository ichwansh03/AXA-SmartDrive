package com.app.smartdrive.api.entities.partner;

public enum BpinStatus {
    PAID("PAID"),
    NOT_PAID("NOT PAID");

    private String name;

    BpinStatus(String name){
        this.name = name;
    }
}
