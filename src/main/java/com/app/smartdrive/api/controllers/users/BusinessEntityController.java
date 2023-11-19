package com.app.smartdrive.api.controllers.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.services.users.implementation.BusinessEntityImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bsns")
@RequiredArgsConstructor
public class BusinessEntityController {
  private final BusinessEntityImpl service;

  @GetMapping
  public ResponseEntity<?> getAll(){
    List<BusinessEntity> l = service.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(l);
  }
}
