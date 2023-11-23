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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/tewo")
@Tag(name = "Master Module")
public class TewoController implements BaseController<TewoDto, Long> {
    private final TewoService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        TemplateTaskWorkOrder tewo = service.getById(id);
        TewoDto result = TransactionMapper.mapEntityToDto(tewo, TewoDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TewoDto request) {
        TemplateTaskWorkOrder result = new TemplateTaskWorkOrder();
        result = TransactionMapper.mapDtoToEntity(request, result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TewoDto request) {
        TemplateTaskWorkOrder result = service.getById(request.getTewoId());
        result = TransactionMapper.mapDtoToEntity(request, result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
