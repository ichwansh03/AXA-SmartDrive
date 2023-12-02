package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.users.BusinessEntityService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BusinessEntityImpl implements BusinessEntityService{
  private final BusinessEntityRepository repo;
  private final EntityManager entityManager;
  @Override
  public List<BusinessEntity> getAll() {
    // TODO Auto-generated method stub
    return repo.findAll();
  }

  

  @Transactional
  public Long save(BusinessEntity businessEntity){
    entityManager.persist(businessEntity);
    Long id = businessEntity.getEntityId();
    entityManager.flush();
    return id;
  }



  @Override
  public BusinessEntity createBusinessEntity() {
    // TODO Auto-generated method stub
    BusinessEntity businessEntity = new BusinessEntity();
    businessEntity.setEntityModifiedDate(LocalDateTime.now());
    entityManager.persist(businessEntity);
    return businessEntity;
  }
  

}
