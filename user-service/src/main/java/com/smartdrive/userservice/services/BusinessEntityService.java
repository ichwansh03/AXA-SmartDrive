package com.smartdrive.userservice.services;


import com.smartdrive.userservice.entities.BusinessEntity;

import java.util.List;

public interface BusinessEntityService {
  List<BusinessEntity> getAll();

  BusinessEntity createBusinessEntity();

  BusinessEntity save(BusinessEntity businessEntity);

}
