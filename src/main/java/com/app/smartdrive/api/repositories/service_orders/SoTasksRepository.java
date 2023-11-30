package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface SoTasksRepository extends JpaRepository<ServiceOrderTasks, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ServiceOrderTasks findSeotById(Long seotId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServiceOrderTasks save(ServiceOrderTasks entity);
}
