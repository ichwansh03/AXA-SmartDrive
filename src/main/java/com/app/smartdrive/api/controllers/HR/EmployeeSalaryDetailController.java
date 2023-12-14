package com.app.smartdrive.api.controllers.HR;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;
import com.app.smartdrive.api.services.HR.EmployeeSalaryDetailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/esd")
public class EmployeeSalaryDetailController {
    
    private final EmployeeSalaryDetailService employeeSalaryDetailService;

    @PostMapping("/generate/{entityId}")
    public ResponseEntity<List<EmployeeSalaryDetail>> generateSalaryDetails(@PathVariable("entityId") Long entityId) {
        List<EmployeeSalaryDetail> salaryDetails = employeeSalaryDetailService.generateSalaryDetails(entityId);
        return new ResponseEntity<>(salaryDetails, HttpStatus.OK);
    }
}
