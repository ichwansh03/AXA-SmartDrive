package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TemplateServiceTaskDto;
import com.app.smartdrive.api.dto.master.TewoDto;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/testa")
@Tag(name = "Master Module")
public class TestaController implements BaseController<TemplateServiceTaskDto, Long> {
    private final TestaService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<TemplateServiceTask> templateServiceTasks = service.getAll();
        List<TemplateServiceTaskDto> result = templateServiceTasks.stream().map(testa -> {
            return new TemplateServiceTaskDto(testa.getTestaId(), testa.getTestaName(), testa.getTestaId(), testa.getTestaGroup(), testa.getTestaCallMethod(), testa.getTestaSeqOrder(), TransactionMapper.mapEntityListToDtoList(testa.getTemplateTaskWorkOrder(), TewoDto.class));
        }).toList();
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        TemplateServiceTask testa = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(testa, TemplateServiceTaskDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TemplateServiceTaskDto request) {
        TemplateServiceTask result = new TemplateServiceTask();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.OK);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TemplateServiceTaskDto request) {
        TemplateServiceTask result = service.getById(request.getTestaId());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.OK);
    }
}
