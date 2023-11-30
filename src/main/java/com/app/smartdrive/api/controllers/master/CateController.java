package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.master.CategoryDto;
import com.app.smartdrive.api.dto.master.TemplateInsurancePremiDto;
import com.app.smartdrive.api.entities.master.Category;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.CateService;
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
@RequestMapping("/master/category")
@Tag(name = "Master Module")
public class CateController implements BaseController<CategoryDto, Long> {
    private final CateService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<Category> cate = service.getAll();
        List<CategoryDto> result = cate.stream().map(category -> {
            return new CategoryDto(category.getCateId(), category.getCateName(), TransactionMapper.mapEntityListToDtoList(category.getTemplateInsurancePremis(), TemplateInsurancePremiDto.class));
        }).toList();
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable Long id) {
        Category category = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(category, CategoryDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody CategoryDto request) {
        Category result = new Category();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody CategoryDto request) {
        Category result = service.getById(request.getCateId());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
