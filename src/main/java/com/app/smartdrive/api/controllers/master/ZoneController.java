package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.ZonesDto;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/zones")
@Tag(name = "Master Module")
public class ZoneController implements BaseController<ZonesDto, Long> {
    private final ZoneService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), ZonesDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody ZonesDto request) {
        return ResponseEntity.ok(service.save(TransactionMapper.mapDtoToEntity(request, new Zones())));
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody ZonesDto request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(request.getZonesId()))), HttpStatus.CREATED);
    }
}
