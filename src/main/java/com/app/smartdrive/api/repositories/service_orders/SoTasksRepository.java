package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface SoTasksRepository extends JpaRepository<ServiceOrderTasks, Long> {
    Stream<ServiceOrderTasks> findAllBySeotSeroId(Long seroId);
}
