package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TemplateTypeDto;
import com.app.smartdrive.api.entities.master.TemplateType;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.TetyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/tety")
@Tag(name = "Master Module")
public class TetyController implements BaseController<TemplateTypeDto, Long> {
    private final TetyService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), TemplateTypeDto.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), TemplateTypeDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TemplateTypeDto request) {
        TemplateType result = new TemplateType();
        result.setTetyGroup(String.valueOf(request.getTetyGroup()));
        result.setTetyName(String.valueOf(request.getTetyName()));
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TemplateTypeDto request) {
        TemplateType result = service.getById(request.getTetyId());
        result.setTetyGroup(String.valueOf(request.getTetyGroup()));
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
