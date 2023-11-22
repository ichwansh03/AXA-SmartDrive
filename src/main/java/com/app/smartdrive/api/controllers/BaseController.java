package com.app.smartdrive.api.controllers;

import org.springframework.http.ResponseEntity;

public interface BaseController<T, ID> {
    ResponseEntity<?> findAllData();

    ResponseEntity<?> findDataById(Long id);

    ResponseEntity<?> saveData(T request);
    ResponseEntity<?> updateData(T request);

    ResponseEntity<?> destroyData(Long id);
}
