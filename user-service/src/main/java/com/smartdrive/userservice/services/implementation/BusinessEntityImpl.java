package com.smartdrive.userservice.services.implementation;

import com.smartdrive.userservice.entities.BusinessEntity;
import com.smartdrive.userservice.repositories.BusinessEntityRepository;
import com.smartdrive.userservice.services.BusinessEntityService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessEntityImpl implements BusinessEntityService {
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
