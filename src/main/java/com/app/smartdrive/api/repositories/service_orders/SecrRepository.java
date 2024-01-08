package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.ServicePremiCreditId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SecrRepository extends JpaRepository<ServicePremiCredit, ServicePremiCreditId> {

    List<ServicePremiCredit> findByServices_ServId(Long servId);

    ServicePremiCredit findBySecrDuedateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT * from so.service_premi_credit WHERE secr_patr_trxno IS NULL", nativeQuery = true)
    List<ServicePremiCredit> findAllTrxnoIsNull();

    List<ServicePremiCredit> findBySecrTrxDateNotNull();
}
