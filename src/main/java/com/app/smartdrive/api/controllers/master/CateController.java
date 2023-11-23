package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.CategoryDto;
import com.app.smartdrive.api.entities.master.Category;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.CateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/category")
public class CateController implements BaseController<CategoryDto, Long> {
    private final CateService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        Category category = service.getById(id);
        CategoryDto result = TransactionMapper.mapEntityToDto(category, CategoryDto.class);
        return ResponseEntity.ok(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CategoryDto request) {
        Category result = new Category();
        result = TransactionMapper.mapDtoToEntity(request,result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody CategoryDto request) {
        Category result = service.getById(request.getCateId());
        result = TransactionMapper.mapDtoToEntity(request,result);
        return new ResponseEntity<>(service.save(result), HttpStatus.CREATED);
    }
}
