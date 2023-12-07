package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SoTasksRepository extends JpaRepository<ServiceOrderTasks, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ServiceOrderTasks> findByServiceOrders_SeroId(String seroId);

    @Modifying(clearAutomatically = true)
    @Transactional
    int updateTasksStatus(@Param("seotStatus") EnumModuleServiceOrders.SeotStatus seotStatus, @Param("seotId") Long seotId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServiceOrderTasks save(ServiceOrderTasks entity);
}
