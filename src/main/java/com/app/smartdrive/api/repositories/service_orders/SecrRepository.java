package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.ServicePremiCreditId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SecrRepository extends JpaRepository<ServicePremiCredit, ServicePremiCreditId> {

    ServicePremiCredit findBySecrDuedateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT * from so.service_premi_credit WHERE secr_patr_trxno IS NULL", nativeQuery = true)
    List<ServicePremiCredit> findAllTrxnoIsNull();
}
