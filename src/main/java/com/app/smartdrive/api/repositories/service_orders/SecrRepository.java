package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.ServicePremiCreditId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface SecrRepository extends JpaRepository<ServicePremiCredit, ServicePremiCreditId> {

    ServicePremiCredit findBySecrDuedateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
