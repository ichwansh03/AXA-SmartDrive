package com.app.smartdrive.api.services.users.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.users.BusinessEntityService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BusinessEntityImpl implements BusinessEntityService{
  private final BusinessEntityRepository repo;
  @Override
  public List<BusinessEntity> getAll() {
    // TODO Auto-generated method stub
    return repo.findAll();
  }
  
}
