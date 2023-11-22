package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.InsuranceTypeDto;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.services.master.implementation.IntyServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/inty")
public class IntyController {
    private final IntyServiceImpl service;

    @GetMapping
    public ResponseEntity<?> findAllInty() {
        return ResponseEntity.ok(service.findAllInsuranceType());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findIntyById(@PathVariable String id) {
        InsuranceType insuranceType = service.getById(id);
        InsuranceTypeDto insuranceTypeDto = new InsuranceTypeDto();
        insuranceTypeDto.setIntyName(insuranceType.getIntyName());
        insuranceTypeDto.setIntyName(insuranceType.getIntyDesc());
        return ResponseEntity.ok(insuranceTypeDto);
    }

    @PostMapping
    public ResponseEntity<?> saveProvinsi(@Valid @RequestBody InsuranceTypeDto request) {
        InsuranceType insuranceType = new InsuranceType();
        insuranceType.setIntyName(request.getIntyName());
        insuranceType.setIntyDesc(request.getIntyDesc());
        return new ResponseEntity<>(service.save(insuranceType), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProvinsi(@Valid @RequestBody InsuranceTypeDto request) {
        InsuranceType insuranceType = service.getById(request.getIntyName());
        insuranceType.setIntyDesc(request.getIntyDesc());
        return ResponseEntity.ok(service.save(insuranceType));
    }
}
