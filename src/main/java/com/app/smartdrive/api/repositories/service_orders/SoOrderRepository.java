package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoOrderRepository extends JpaRepository<ServiceOrders, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServiceOrders save(ServiceOrders entity);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ServiceOrders> findAllSeroByServId(@Param("servId") Long servId);
}
