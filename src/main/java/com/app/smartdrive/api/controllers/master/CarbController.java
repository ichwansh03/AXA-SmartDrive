package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.CarBrandDto;
import com.app.smartdrive.api.entities.master.CarBrand;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.CarbService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/carb")
@Tag(name = "Master Module")
public class CarbController implements BaseController<CarBrandDto, Long> {
    private final CarbService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<CarBrand> carBrands = service.getAll();
        List<CarBrandDto> result = TransactionMapper.mapEntityListToDtoList(carBrands, CarBrandDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        CarBrand carBrand = service.getById(id);
        CarBrandDto result = TransactionMapper.mapEntityToDto(carBrand, CarBrandDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CarBrandDto request) {
        CarBrand result = new CarBrand();
        result = TransactionMapper.mapDtoToEntity(request, result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody CarBrandDto request) {
        CarBrand result = service.getById(request.getCabrID());
        result = TransactionMapper.mapDtoToEntity(request, result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
