package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface SecrRepository extends JpaRepository<ServicePremiCredit, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ServicePremiCredit> findAllBySecrServId(Long servId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServicePremiCredit save(ServicePremiCredit entity);
}
