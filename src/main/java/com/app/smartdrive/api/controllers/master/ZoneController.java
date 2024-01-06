package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.ZoneReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/zones")
@CrossOrigin
@Tag(name = "Master Module")
public class ZoneController implements MasterController<ZoneReq, Long> {
    private final MasterService zoneServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(zoneServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(zoneServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody ZoneReq request) {
        return ResponseEntity.ok(zoneServiceImpl.save(request));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody ZoneReq request) {
        return new ResponseEntity<>(zoneServiceImpl.update(id, request), HttpStatus.CREATED);
    }
}
