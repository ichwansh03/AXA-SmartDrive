package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TemplateInsurancePremiDto;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.TemiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/temi")
public class TemiController implements BaseController<TemplateInsurancePremiDto, Long> {
    private final TemiService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        TemplateInsurancePremi temi = service.getById(id);
        TemplateInsurancePremiDto result = TransactionMapper.mapEntityToDto(temi, TemplateInsurancePremiDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TemplateInsurancePremiDto request) {
        TemplateInsurancePremi result = new TemplateInsurancePremi();
        result = TransactionMapper.mapDtoToEntity(request,result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TemplateInsurancePremiDto request) {
        TemplateInsurancePremi result = service.getById(request.getTemiId());
        result = TransactionMapper.mapDtoToEntity(request,result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
