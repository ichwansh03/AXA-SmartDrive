package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.response.CarbRes;
import com.app.smartdrive.api.dto.master.request.CarbReq;
import com.app.smartdrive.api.entities.master.CarBrand;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.CarbService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/carb")
@Tag(name = "Master Module")
public class CarbController implements BaseController<CarbReq, Long> {
    private final CarbService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), CarbRes.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), CarbRes.class));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CarbReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new CarBrand())), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable Long id, @Valid @RequestBody CarbReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}
