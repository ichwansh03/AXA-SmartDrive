package com.app.smartdrive.api.controllers.Master;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/category")
public class CategoryController {
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok("a");
    }
}
