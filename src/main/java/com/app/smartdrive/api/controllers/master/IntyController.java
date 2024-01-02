package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.request.IntyReq;
import com.app.smartdrive.api.services.master.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/inty")
@Tag(name = "Master Module")
public class IntyController implements MasterController<IntyReq, String> {
    private final MasterService intyServiceImpl;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(intyServiceImpl.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        return ResponseEntity.ok(intyServiceImpl.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody IntyReq request) {
        return new ResponseEntity<>(intyServiceImpl.save(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable String id, @Valid @RequestBody IntyReq request) {
        intyServiceImpl.getById(id);
        return new ResponseEntity<>(intyServiceImpl.save(request), HttpStatus.CREATED);
    }
}
