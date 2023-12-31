package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.Services;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SoRepository extends JpaRepository<Services, Long> {

    //execute one by one data simultaneously when data changed concurrent
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    Services save(Services services);

    @Transactional(readOnly = true)
    Optional<Services> getServiceParent(Long servId);
}
