package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.response.CarsRes;
import com.app.smartdrive.api.dto.master.request.CarsReq;
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
public class CarsController implements BaseController<CarsReq, Long> {
    private final CarsService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), CarsRes.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), CarsRes.class));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CarsReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new CarSeries())), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody CarsReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}
