package com.app.smartdrive.api.controllers.Master;

import com.app.smartdrive.api.services.master.implementation.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/category")
public class CategoryController {
    private final CategoryServiceImpl service;
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(service.findAll());
    }

}
