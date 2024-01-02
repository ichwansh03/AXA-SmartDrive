package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.List;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommonUtils {
  

     public static boolean checkBusinesEntity(Long id, BusinessEntityRepository repositoryBisnis) {
        List<BusinessEntity> businesData = repositoryBisnis.findAll();
        for (BusinessEntity bisnis : businesData) {
            if(id.equals(bisnis.getEntityId())){
                bisnis.setEntityModifiedDate(LocalDateTime.now());
                repositoryBisnis.save(bisnis);
                return true;
            }
        }
        return false;
    }
}
