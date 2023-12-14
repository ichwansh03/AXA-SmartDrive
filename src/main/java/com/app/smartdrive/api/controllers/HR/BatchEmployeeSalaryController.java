package com.app.smartdrive.api.controllers.HR;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.services.HR.BatchEmployeeSalaryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bes")
public class BatchEmployeeSalaryController {

    private final BatchEmployeeSalaryService batchEmployeeSalaryService;

    @PostMapping("/create/{id}")
    public ResponseEntity<BatchEmployeeSalary> createOne(@PathVariable("id") Long id){
        BatchEmployeeSalary batchEmployeeSalary = batchEmployeeSalaryService.createOne(id);
        return ResponseEntity.ok(batchEmployeeSalary);
    }

    @GetMapping
    public ResponseEntity<List<BatchEmployeeSalary>> getAllTransNull(){
        return ResponseEntity.ok(batchEmployeeSalaryService.getAllTransNull());
    }
}
