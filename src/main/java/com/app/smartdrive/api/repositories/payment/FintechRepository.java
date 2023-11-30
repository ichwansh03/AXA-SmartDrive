package com.app.smartdrive.api.repositories.payment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;

import jakarta.transaction.Transactional;

public interface FintechRepository extends JpaRepository<Fintech, Long> {
    @Query(value = "SELECT * FROM PAYMENT.FINTECH WHERE FINT_NAME = ?1", nativeQuery = true)
    Optional<Fintech> findByFintNameOptional(String fint_name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from payment.fintech where fint_entityid=:fint_entityid", nativeQuery = true)
    int deleteFintechById(Long fint_entityid);
}
