package com.app.smartdrive.api.services.users.implementation;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.users.BusinessEntityService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessEntityImpl implements BusinessEntityService{
  private final BusinessEntityRepository repo;
  private final EntityManager entityManager;
  @Override
  public List<BusinessEntity> getAll() {
    // TODO Auto-generated method stub
    return repo.findAll();
  }

  

  @Transactional
  public BusinessEntity save(BusinessEntity businessEntity){
    BusinessEntity business = repo.save(businessEntity);
    log.info("business entity id ini cuy "+business.getEntityId().toString());
//    repo.flush();
    return business;
  }



  @Override
  public BusinessEntity createBusinessEntity() {
    // TODO Auto-generated method stub
    BusinessEntity businessEntity = new BusinessEntity();
    businessEntity.setEntityModifiedDate(LocalDateTime.now());
    BusinessEntity newBusiness = save(businessEntity);
    return newBusiness;
  }
}
