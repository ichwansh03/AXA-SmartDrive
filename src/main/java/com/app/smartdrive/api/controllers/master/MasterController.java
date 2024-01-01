package com.app.smartdrive.api.controllers.master;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Master Module")
public interface MasterController<T, ID> {
    ResponseEntity<?> findAllData();

    ResponseEntity<?> findDataById(ID id);

    ResponseEntity<?> saveData(T request);
    ResponseEntity<?> updateData(ID id ,T request);
}
