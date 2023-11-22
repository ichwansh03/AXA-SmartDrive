package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.TemplateInsurancePremiDto;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.services.master.TemiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

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
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody TemplateInsurancePremiDto request) {
        TemplateInsurancePremi result = new TemplateInsurancePremi();
        result = transactionMethod(result, request);

        result = transactionMethod(result, request);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody TemplateInsurancePremiDto request) {
        TemplateInsurancePremi result = service.getById(request.getTemiId());
        result = transactionMethod(result, request);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroyData(@PathVariable Long id) {
        return null;
    }

    public TemplateInsurancePremi transactionMethod(TemplateInsurancePremi result, TemplateInsurancePremiDto request) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        modelMapper.map(request, result);
        return result;
    }
}
