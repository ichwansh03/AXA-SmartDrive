package com.app.smartdrive.api.controllers.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.repositories.master.IntyRepository;

@RestController
@RequestMapping("/type")
public class InsuranceTypeController {
    @Autowired
    IntyRepository repository;

    @GetMapping
    public List<InsuranceType> get(){
        return this.repository.findAll();
    }
}
