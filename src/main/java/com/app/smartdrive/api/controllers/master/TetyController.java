package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TemplateTypeDto;
import com.app.smartdrive.api.entities.master.TemplateType;
import com.app.smartdrive.api.services.master.TetyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/tety")
public class TetyController implements BaseController<TemplateTypeDto, Long> {
    private final TetyService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TemplateTypeDto request) {
        TemplateType result = new TemplateType();
        result.setTetyGroup(String.valueOf(request.getTetyGroup()));
        result.setTetyName(String.valueOf(request.getTetyName()));
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TemplateTypeDto request) {
        TemplateType result = service.getById(request.getTetyId());
        result.setTetyGroup(String.valueOf(request.getTetyGroup()));
        result.setTetyName(String.valueOf(request.getTetyName()));
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<?> destroyData(@PathVariable Long aLong) {
        return null;
    }
}
