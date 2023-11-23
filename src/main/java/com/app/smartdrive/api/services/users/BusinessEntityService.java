package com.app.smartdrive.api.services.users;

import java.util.List;

import com.app.smartdrive.api.entities.users.BusinessEntity;

public interface BusinessEntityService {
  List<BusinessEntity> getAll();
  Long save(BusinessEntity businessEntity);
  
}
