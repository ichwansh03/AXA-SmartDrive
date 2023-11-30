package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.RegionPlatDto;
import com.app.smartdrive.api.entities.master.RegionPlat;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.RegpService;
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
@RequestMapping("/master/regp")
@Tag(name = "Master Module")
public class RegpController implements BaseController<RegionPlatDto, String> {
    private final RegpService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<RegionPlat> regionPlat = service.getAll();
        List<RegionPlatDto> result = TransactionMapper.mapEntityListToDtoList(regionPlat, RegionPlatDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        RegionPlat regionPlat = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(regionPlat, RegionPlatDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody RegionPlatDto request) {
        RegionPlat result = new RegionPlat();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody RegionPlatDto request) {
        RegionPlat result = service.getById(request.getRegpName());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
