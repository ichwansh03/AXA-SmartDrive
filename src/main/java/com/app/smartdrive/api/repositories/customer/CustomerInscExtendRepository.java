package com.app.smartdrive.api.repositories.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.customer.CustomerInscExtend;

@Repository
public interface CustomerInscExtendRepository extends JpaRepository<CustomerInscExtend, Long>{
    
}
