package com.smartdrive.masterservice.controllers;

import com.smartdrive.masterservice.dto.request.CarsReq;
import com.smartdrive.masterservice.dto.response.CarsRes;
import com.smartdrive.masterservice.entities.CarSeries;
import com.smartdrive.masterservice.mapper.TransactionMapper;
import com.smartdrive.masterservice.services.CarsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/car-series")
@Tag(name = "Master Module")
public class CarsController implements BaseController<CarsReq, Long> {
    private final CarsService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), CarsRes.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), CarsRes.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CarsReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new CarSeries())), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody CarsReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}