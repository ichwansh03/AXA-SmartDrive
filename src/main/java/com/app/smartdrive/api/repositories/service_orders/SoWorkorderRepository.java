package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import java.util.stream.Stream;

@Repository
public interface SoWorkorderRepository extends JpaRepository<ServiceOrderWorkorder, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServiceOrderWorkorder save(ServiceOrderWorkorder entity);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ServiceOrderWorkorder findBySowoId(Long sowoId);
}
