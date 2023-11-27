package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.CitiesDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/cities")
@Tag(name = "Master Module")
public class CityController implements BaseController<CitiesDto, Long> {
    private final CityService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<Cities> cities = service.getAll();
        List<CitiesDto> result = TransactionMapper.mapEntityListToDtoList(cities, CitiesDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        Cities cities = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(cities, CitiesDto.class));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CitiesDto request) {
        Cities result = new Cities();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody CitiesDto request) {
        Cities result = service.getById(request.getCityId());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
