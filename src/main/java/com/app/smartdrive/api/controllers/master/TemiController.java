package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TemplateInsurancePremiDto;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.TemiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/temi")
@Tag(name = "Master Module")
public class TemiController implements BaseController<TemplateInsurancePremiDto, Long> {
    private final TemiService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<TemplateInsurancePremi> temi = service.getAll();
        List<TemplateInsurancePremiDto> result = TransactionMapper.mapEntityListToDtoList(temi, TemplateInsurancePremiDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        TemplateInsurancePremi temi = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(temi, TemplateInsurancePremiDto.class));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TemplateInsurancePremiDto request) {
        TemplateInsurancePremi result = new TemplateInsurancePremi();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TemplateInsurancePremiDto request) {
        TemplateInsurancePremi result = service.getById(request.getTemiId());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
