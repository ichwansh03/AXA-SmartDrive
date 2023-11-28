package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TewoDto;
import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.TewoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/tewo")
@Tag(name = "Master Module")
public class TewoController implements BaseController<TewoDto, Long> {
    private final TewoService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<TemplateTaskWorkOrder> tewo = service.getAll();
        List<TewoDto> result = TransactionMapper.mapEntityListToDtoList(tewo, TewoDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        TemplateTaskWorkOrder tewo = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(tewo, TewoDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TewoDto request) {
        TemplateTaskWorkOrder result = new TemplateTaskWorkOrder();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TewoDto request) {
        TemplateTaskWorkOrder result = service.getById(request.getTewoId());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
