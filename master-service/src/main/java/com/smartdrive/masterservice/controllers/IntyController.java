package com.smartdrive.masterservice.controllers;

import com.smartdrive.masterservice.dto.request.IntyReq;
import com.smartdrive.masterservice.dto.response.IntyRes;
import com.smartdrive.masterservice.entities.InsuranceType;
import com.smartdrive.masterservice.mapper.TransactionMapper;
import com.smartdrive.masterservice.services.IntyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/inty")
@Tag(name = "Master Module")
public class IntyController implements BaseController<IntyReq, String> {
    private final IntyService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), IntyRes.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), IntyRes.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody IntyReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new InsuranceType())), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable String id, @Valid @RequestBody IntyReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}
