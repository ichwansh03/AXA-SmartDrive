package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface SemiRepository extends JpaRepository<ServicePremi, Long> {

    List<ServicePremi> findByServices_ServId(Long servId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServicePremi save(ServicePremi entity);
}
