package com.app.smartdrive.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BaseController<T, ID> {
    ResponseEntity<?> findAllData();

    ResponseEntity<?> findDataById(Long id);

    ResponseEntity<?> saveData(T request);
    ResponseEntity<?> updateData(T request);

    ResponseEntity<?> destroyData(Long id);
}
