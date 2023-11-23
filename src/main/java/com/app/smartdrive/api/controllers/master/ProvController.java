package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.ProvinsiDto;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.ProvService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/provinsi")
public class ProvController implements BaseController<ProvinsiDto, Long> {
    private final ProvService service;

    @Override
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        Provinsi provinsi = service.getById(id);
        ProvinsiDto result = TransactionMapper.mapEntityToDto(provinsi, ProvinsiDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody ProvinsiDto request) {
        Provinsi result = new Provinsi();
        result = TransactionMapper.mapDtoToEntity(request, result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody ProvinsiDto request) {
        Provinsi result = service.getById(request.getProvId());
        result = TransactionMapper.mapDtoToEntity(request, result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
