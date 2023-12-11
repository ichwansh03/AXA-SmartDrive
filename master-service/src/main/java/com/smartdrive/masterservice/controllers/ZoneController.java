package com.smartdrive.masterservice.controllers;

import com.smartdrive.masterservice.dto.request.ZoneReq;
import com.smartdrive.masterservice.dto.response.ZonesRes;
import com.smartdrive.masterservice.entities.Zones;
import com.smartdrive.masterservice.mapper.TransactionMapper;
import com.smartdrive.masterservice.services.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/zones")
@Tag(name = "Master Module")
public class ZoneController implements BaseController<ZoneReq, Long> {
    private final ZoneService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), ZonesRes.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody ZoneReq request) {
        return ResponseEntity.ok(service.save(TransactionMapper.mapDtoToEntity(request, new Zones())));
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody ZoneReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}