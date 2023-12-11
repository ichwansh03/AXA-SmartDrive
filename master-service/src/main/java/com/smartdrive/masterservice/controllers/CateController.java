package com.smartdrive.masterservice.controllers;

import com.smartdrive.masterservice.dto.request.CategoryReq;
import com.smartdrive.masterservice.dto.response.CateRes;
import com.smartdrive.masterservice.entities.Category;
import com.smartdrive.masterservice.mapper.TransactionMapper;
import com.smartdrive.masterservice.services.CateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/category")
@Tag(name = "Master Module")
public class CateController implements BaseController<CategoryReq, Long> {
    private final CateService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), CateRes.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), CateRes.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CategoryReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new Category())), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody CategoryReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}
