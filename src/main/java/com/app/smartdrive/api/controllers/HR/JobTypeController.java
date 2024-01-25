package com.app.smartdrive.api.controllers.HR;

import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.services.HR.JobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/jobType")
public class JobTypeController {
    private final JobTypeService jobTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById(@PathVariable("id") String id){
        EmployeesJobTypeResponseDto resultDto = jobTypeService.getById(id);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(jobTypeService.getAll(), HttpStatus.OK);
    }
}
