package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.ProvinsiDto;
import com.app.smartdrive.api.dto.master.request.ProvReq;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.ProvService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/provinsi")
@Tag(name = "Master Module")
public class ProvController implements BaseController<ProvReq, Long> {
    private final ProvService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), ProvinsiDto.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), ProvinsiDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody ProvReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new Provinsi())), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody ProvReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}
