package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TemplateServiceTaskDto;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.TestaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/testa")
public class TestaController implements BaseController<TemplateServiceTaskDto, Long> {
    private final TestaService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TemplateServiceTaskDto request) {
        TemplateServiceTask result = new TemplateServiceTask();
        result = TransactionMapper.mapDtoToEntity(request,result);
        return new ResponseEntity<>(service.save(result), HttpStatus.OK);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TemplateServiceTaskDto request) {
        TemplateServiceTask result = service.getById(request.getTestaId());
        result = TransactionMapper.mapDtoToEntity(request,result);
        return new ResponseEntity<>(service.save(result), HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<?> destroyData(@PathVariable Long aLong) {
        return null;
    }
}
