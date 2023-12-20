package com.smartdrive.userservice.controllers;

import com.smartdrive.userservice.entities.BusinessEntity;
import com.smartdrive.userservice.services.implementation.BusinessEntityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bsns")
@RequiredArgsConstructor
public class BusinessEntityController {
  private final BusinessEntityImpl service;

  @GetMapping
  public ResponseEntity<?> getAll(){
    List<BusinessEntity> businessEntityList = service.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(businessEntityList);
  }
}
