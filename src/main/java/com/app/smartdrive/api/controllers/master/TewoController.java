package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.TewoReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/tewo")
@CrossOrigin
@Tag(name = "Master Module")
public class TewoController implements MasterController<TewoReq, Long> {
    private final MasterService tewoServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(tewoServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(tewoServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TewoReq request) {
        return new ResponseEntity<>(tewoServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody TewoReq request) {
        tewoServiceImpl.getById(id);
        return new ResponseEntity<>(tewoServiceImpl.update(id, request), HttpStatus.CREATED);
    }
}
