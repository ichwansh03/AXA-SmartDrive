package com.app.smartdrive.api.repositories.customer;

import com.app.smartdrive.api.entities.customer.CustomerInscDocId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.customer.CustomerInscDoc;

@Repository
public interface CustomerInscDocRepository extends JpaRepository<CustomerInscDoc, CustomerInscDocId>{
    @Transactional
    void deleteAllByCadocCreqEntityid(Long cadocCreqEntityid);
}
