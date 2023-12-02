package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TemplateServiceTaskDto;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.TestaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/testa")
@Tag(name = "Master Module")
public class TestaController implements BaseController<TemplateServiceTaskDto, Long> {
    private final TestaService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(TransactionMapper.mapEntityListToDtoList(service.getAll(), TemplateServiceTaskDto.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(service.getById(id), TemplateServiceTaskDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TemplateServiceTaskDto request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, new TemplateServiceTask())), HttpStatus.OK);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TemplateServiceTaskDto request) {
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, service.getById(request.getTestaId()))), HttpStatus.OK);
    }
}
