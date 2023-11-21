package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.AreaWorkGroupDto;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.services.master.implementation.ArwgServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/arwg")
public class ArwgController {
    private final ArwgServiceImpl service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody AreaWorkGroupDto areaWorkGroupDto) {
        AreaWorkGroup areaWorkGroup = new AreaWorkGroup();
        areaWorkGroup.setArwgDesc(areaWorkGroupDto.getArwgDesc());
        return new ResponseEntity<>(service.save(areaWorkGroup), HttpStatus.CREATED);
    }

//    @PutMapping("/update")
//    public ResponseEntity<?> updateCategory(@Valid @RequestBody AreaWorkGroup areaWorkGroup) {
//        AreaWorkGroup existedData = service.getById(areaWorkGroup.getArwgCode());
//        existedData.setArwgDesc(areaWorkGroup.getArwgDesc());
//        return new ResponseEntity<>(service.save(existedData), HttpStatus.CREATED);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok("Category ID : " + id + " Deleted");
    }
}
