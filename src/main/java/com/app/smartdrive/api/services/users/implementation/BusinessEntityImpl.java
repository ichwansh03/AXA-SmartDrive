package com.app.smartdrive.api.services.users.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repository.users.BusinessEntityRepo;
import com.app.smartdrive.api.services.users.BusinessEntityService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BusinessEntityImpl implements BusinessEntityService{
  private final BusinessEntityRepo repo;
  @Override
  public List<BusinessEntity> getAll() {
    // TODO Auto-generated method stub
    return repo.findAll();
  }
  
}
