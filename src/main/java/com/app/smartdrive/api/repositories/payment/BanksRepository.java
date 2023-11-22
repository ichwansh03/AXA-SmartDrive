package com.app.smartdrive.api.repositories.payment;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.payment.Banks;

import jakarta.transaction.Transactional;

@Repository
public interface BanksRepository extends JpaRepository<Banks,Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE from payment.banks WHERE bank_entityid=:bank_entityid",nativeQuery = true)
    int deleteByID(Long bank_entityid);

 
    
}