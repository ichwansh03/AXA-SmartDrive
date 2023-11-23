package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.CarSeriesDto;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.CarsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/car-series")
@Tag(name = "Master Module")
public class CarsController implements BaseController<CarSeriesDto, Long> {
    private final CarsService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        CarSeries carSeries = service.getById(id);
        CarSeriesDto result = TransactionMapper.mapEntityToDto(carSeries, CarSeriesDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CarSeriesDto request) {
        CarSeries result = new CarSeries();
        result = TransactionMapper.mapDtoToEntity(request,result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody CarSeriesDto request) {
        CarSeries result = service.getById(request.getCarsId());
        result = TransactionMapper.mapDtoToEntity(request,result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
