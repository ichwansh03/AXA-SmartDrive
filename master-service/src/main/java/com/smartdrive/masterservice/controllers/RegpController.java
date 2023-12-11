package com.smartdrive.masterservice.controllers;

import com.smartdrive.masterservice.dto.request.RegpReq;
import com.smartdrive.masterservice.dto.response.RegpRes;
import com.smartdrive.masterservice.entities.RegionPlat;
import com.smartdrive.masterservice.mapper.TransactionMapper;
import com.smartdrive.masterservice.services.RegpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/regp")
@Tag(name = "Master Module")
public class RegpController implements BaseController<RegpReq, String> {
    private final RegpService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), RegpRes.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), RegpRes.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody RegpReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new RegionPlat())), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable String id, @Valid @RequestBody RegpReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}
