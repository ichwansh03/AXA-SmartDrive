package com.app.smartdrive.api.repositories.users;

import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.users.BusinessEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BusinessEntityRepo extends JpaRepository<BusinessEntity,Long>{
  
}
