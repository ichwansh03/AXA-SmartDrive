package com.smartdrive.masterservice.controllers;

import org.springframework.http.ResponseEntity;

public interface BaseController<T, ID> {
    ResponseEntity<?> findAllData();

    ResponseEntity<?> findDataById(ID id);

    ResponseEntity<?> saveData(T request);
    ResponseEntity<?> updateData(ID id ,T request);

    default ResponseEntity<?> destroyData(ID id) {
        return null;
    }
}
