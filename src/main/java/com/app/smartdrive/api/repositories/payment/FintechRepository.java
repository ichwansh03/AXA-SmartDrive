package com.app.smartdrive.api.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.smartdrive.api.entities.payment.Fintech;

import jakarta.transaction.Transactional;

public interface FintechRepository extends JpaRepository<Fintech, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from payment.fintech where fint_entityid=:fint_entityid", nativeQuery = true)
    void deleteFintechById(Long fint_entityid);
}
