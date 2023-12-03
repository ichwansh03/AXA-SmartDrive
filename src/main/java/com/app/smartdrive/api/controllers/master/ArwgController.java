package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.AreaWorkGroupDto;
import com.app.smartdrive.api.dto.master.request.ArwgReq;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.ArwgService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/arwg")
@Tag(name = "Master Module", description = "This Tab Contains All Operation for Master Module")
public class ArwgController implements BaseController<ArwgReq, String> {
    private final ArwgService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), AreaWorkGroupDto.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), AreaWorkGroupDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody ArwgReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new AreaWorkGroup())), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateData(@PathVariable String id,@Valid @RequestBody ArwgReq request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(id))), HttpStatus.CREATED);
    }
}
