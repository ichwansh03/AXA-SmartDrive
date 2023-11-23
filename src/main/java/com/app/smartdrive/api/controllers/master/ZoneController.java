package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.ZonesDto;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/zones")
@Tag(name = "Master Module")
public class ZoneController {
    private final ZoneService service;

    @GetMapping
    public ResponseEntity<?> findAllZones() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findZoneById(@PathVariable Long id) {
        Zones zones = service.getById(id);
        ZonesDto result = TransactionMapper.mapEntityToDto(zones, ZonesDto.class);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> saveZone(@Valid @RequestBody ZonesDto request) {
        Zones result = new Zones();
        result = TransactionMapper.mapDtoToEntity(request, result);
        return ResponseEntity.ok(service.save(result));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateZoneById(@Valid @RequestBody ZonesDto request) {
        Zones result = service.getById(request.getZonesId());
        result = TransactionMapper.mapDtoToEntity(request, result);
        return ResponseEntity.ok(service.save(result));
    }
}
