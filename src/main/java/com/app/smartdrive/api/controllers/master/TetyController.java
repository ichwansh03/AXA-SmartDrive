package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.response.TetyRes;
import com.app.smartdrive.api.dto.master.request.TetyReq;
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
public class TetyController implements BaseController<TetyReq, Long> {
    private final TetyService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), TetyRes.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), TetyRes.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TetyReq request) {
        TemplateType result = new TemplateType();
        result.setTetyGroup(String.valueOf(request.getTetyGroup()));
        result.setTetyName(String.valueOf(request.getTetyName()));
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody TetyReq request) {
        TemplateType result = service.getById(id);
        result.setTetyGroup(String.valueOf(request.getTetyGroup()));
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
