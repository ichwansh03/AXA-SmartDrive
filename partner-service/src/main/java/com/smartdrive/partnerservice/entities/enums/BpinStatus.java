package com.smartdrive.partnerservice.entities.enums;

public enum BpinStatus {
    PAID("PAID"),
    NOT_PAID("NOT PAID");

    private String name;

    BpinStatus(String name){
        this.name = name;
    }
}
