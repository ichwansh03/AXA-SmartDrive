package com.app.smartdrive.api.repositories.payment;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.payment.Banks;

import jakarta.transaction.Transactional;


@Repository
public interface BanksRepository extends JpaRepository<Banks,Long> {
    @Query(value = "SELECT * FROM PAYMENT.BANKS WHERE BANK_NAME = ?1", nativeQuery = true)
    Optional<Banks> findByBankNameOptional(String bank_name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE from payment.banks WHERE bank_entityid=:bank_entityid",nativeQuery = true)
    int deleteByID(Long bank_entityid);

 
    
}