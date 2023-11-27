package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.CarModelDto;
import com.app.smartdrive.api.entities.master.CarModel;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.CarmService;
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
@RequestMapping("/master/carm")
@Tag(name = "Master Module")
public class CarmController implements BaseController<CarModelDto, Long> {
    private final CarmService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<CarModel> carModel = service.getAll();
        List<CarModelDto> result = TransactionMapper.mapEntityListToDtoList(carModel, CarModelDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        CarModel carModel = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(carModel, CarModelDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CarModelDto request) {
        CarModel result = new CarModel();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody CarModelDto request) {
        CarModel result = service.getById(request.getCarmId());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
