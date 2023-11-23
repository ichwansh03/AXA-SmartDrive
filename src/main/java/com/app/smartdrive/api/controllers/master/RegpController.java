package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.RegionPlatDto;
import com.app.smartdrive.api.entities.master.RegionPlat;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.RegpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/regp")
public class RegpController implements BaseController<RegionPlatDto, String> {
    private final RegpService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        RegionPlat regionPlat = service.getById(id);
        RegionPlatDto result = TransactionMapper.mapEntityToDto(regionPlat, RegionPlatDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody RegionPlatDto request) {
        RegionPlat result = new RegionPlat();
        result = TransactionMapper.mapDtoToEntity(request, result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody RegionPlatDto request) {
        RegionPlat result = service.getById(request.getRegpName());
        result = TransactionMapper.mapDtoToEntity(request, result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
