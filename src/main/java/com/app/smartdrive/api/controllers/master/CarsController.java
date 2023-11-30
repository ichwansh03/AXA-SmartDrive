package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.dto.master.CarSeriesDto;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.CarsService;
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
@RequestMapping("/master/car-series")
@Tag(name = "Master Module")
public class CarsController implements BaseController<CarSeriesDto, Long> {
    private final CarsService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<CarSeries> carSeries = service.getAll();
        List<CarSeriesDto> result = carSeries.stream().map(series -> {
            return new CarSeriesDto(series.getCarsId(), series.getCarsName(), series.getCarsPassenger(), series.getCarsCarmId(), TransactionMapper.mapEntityListToDtoList(series.getCustomerInscAssets(), CiasDTO.class));
        }).toList();
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        CarSeries carSeries = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(carSeries, CarSeriesDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CarSeriesDto request) {
        CarSeries result = new CarSeries();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody CarSeriesDto request) {
        CarSeries result = service.getById(request.getCarsId());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
