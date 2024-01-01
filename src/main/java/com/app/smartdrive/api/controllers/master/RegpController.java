package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.RegpReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/regp")
@Tag(name = "Master Module")
public class RegpController implements MasterController<RegpReq, String> {
    private final MasterService regpServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(regpServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        return ResponseEntity.ok(regpServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody RegpReq request) {
        return new ResponseEntity<>(regpServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable String id, @Valid @RequestBody RegpReq request) {
        regpServiceImpl.getById(id);
        return new ResponseEntity<>(regpServiceImpl.save(request), HttpStatus.CREATED);
    }
}
