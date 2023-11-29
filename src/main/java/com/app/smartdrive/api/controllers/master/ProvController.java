package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.CitiesDto;
import com.app.smartdrive.api.dto.master.ProvinsiDto;
import com.app.smartdrive.api.dto.master.RegionPlatDto;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/provinsi")
@Tag(name = "Master Module")
public class ProvController implements BaseController<ProvinsiDto, Long> {
    private final ProvService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<Provinsi> provinces = service.getAll();
        List<ProvinsiDto> result = provinces.stream().map(provinsi -> {
            return new ProvinsiDto(provinsi.getProvId(), provinsi.getProvName(), provinsi.getProvZonesId(), TransactionMapper.mapEntityListToDtoList(provinsi.getCities(), CitiesDto.class), TransactionMapper.mapEntityListToDtoList(provinsi.getRegionPlats(), RegionPlatDto.class));
        }).toList();
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        Provinsi provinsi = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(provinsi, ProvinsiDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody ProvinsiDto request) {
        Provinsi result = new Provinsi();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody ProvinsiDto request) {
        Provinsi result = service.getById(request.getProvId());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
