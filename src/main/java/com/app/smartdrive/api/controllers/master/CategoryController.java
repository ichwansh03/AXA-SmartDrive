package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.dto.master.CategoryDto;
import com.app.smartdrive.api.entities.master.Category;
import com.app.smartdrive.api.services.master.implementation.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/category")
public class CategoryController {
    private final CategoryServiceImpl service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        category.setCateName(categoryDto.getCateName());
        return new ResponseEntity<>(service.save(category), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category existedData = service.getById(categoryDto.getCateId());
        existedData.setCateName(categoryDto.getCateName());
        return new ResponseEntity<>(service.save(existedData), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Category ID : " + id + " Deleted");
    }
}
