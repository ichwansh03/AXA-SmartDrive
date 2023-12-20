package com.smartdrive.userservice.repositories;


import com.smartdrive.userservice.entities.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessEntityRepository extends JpaRepository<BusinessEntity,Long>{
  
}
