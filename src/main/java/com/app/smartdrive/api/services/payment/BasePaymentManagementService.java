package com.app.smartdrive.api.services.payment;

import java.util.List;


public interface BasePaymentManagementService<T, ID> {
    T getById(ID id);
    List<T> getAll();
    default  Boolean deleteById(ID id){
        return true;
    };
} 