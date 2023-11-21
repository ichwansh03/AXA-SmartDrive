package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.ProvinsiDto;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.services.master.implementation.ProvinsiServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/provinsi")
public class ProvinsiController {
    private final ProvinsiServiceImpl service;

    @GetMapping
    public ResponseEntity<?> findAllProvinsi() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProvinsiById(@PathVariable Long id) {
        Provinsi provinsi = service.getById(id);
        ProvinsiDto provinsiDto = new ProvinsiDto();
        provinsiDto.setProvId(provinsi.getProvId());
        provinsiDto.setProvName(provinsi.getProvName());
        provinsiDto.setProv_zones_id(provinsi.getProv_zones_id());
        return ResponseEntity.ok(provinsiDto);
    }

    @PostMapping
    public ResponseEntity<?> saveProvinsi(@Valid @RequestBody ProvinsiDto request) {
        Provinsi provinsi = new Provinsi();
        provinsi.setProvName(request.getProvName());
        provinsi.setProv_zones_id(request.getProv_zones_id());
        return new ResponseEntity<>(service.save(provinsi), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProvinsi(@Valid @RequestBody ProvinsiDto request) {
        Provinsi provinsi = service.getById(request.getProvId());
        provinsi.setProvName(request.getProvName());
        return new ResponseEntity<>(service.save(provinsi), HttpStatus.CREATED);
    }
}
