package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.InsuranceTypeDto;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.IntyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/inty")
@Tag(name = "Master Module")
public class IntyController implements BaseController<InsuranceTypeDto, String> {
    private final IntyService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<InsuranceType> insuranceType = service.getAll();
        List<InsuranceTypeDto> result = TransactionMapper.mapEntityListToDtoList(insuranceType, InsuranceTypeDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        InsuranceType insuranceType = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(insuranceType, InsuranceTypeDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody InsuranceTypeDto request) {
        InsuranceType result = new InsuranceType();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody InsuranceTypeDto request) {
        InsuranceType result = service.getById(request.getIntyName());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
