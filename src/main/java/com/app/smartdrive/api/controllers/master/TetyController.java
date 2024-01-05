package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.TetyReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/master/tety")
@Tag(name = "Master Module")
public class TetyController implements MasterController<TetyReq, Long> {
    private final MasterService tetyServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(tetyServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(tetyServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TetyReq request) {
        return new ResponseEntity<>(tetyServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody TetyReq request) {
        tetyServiceImpl.getById(id);
        return new ResponseEntity<>(tetyServiceImpl.save(request), HttpStatus.CREATED);
    }
}
