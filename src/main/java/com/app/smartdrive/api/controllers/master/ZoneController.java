package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.ZonesDto;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.services.master.implementation.ZoneServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/zones")
public class ZoneController {
    private final ZoneServiceImpl service;

    @GetMapping
    public ResponseEntity<?> findAllZones() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findZoneById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveZone(@Valid @RequestBody ZonesDto request) {
        Zones zones = new Zones();
        zones.setZonesName(request.getZonesName());
        return ResponseEntity.ok(service.save(zones));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateZoneById(@Valid @RequestBody ZonesDto request) {
        Zones zones = service.getById(request.getZonesId());
        zones.setZonesName(request.getZonesName());
        return ResponseEntity.ok(service.save(zones));
    }
}
