package com.app.smartdrive.api.repositories.service_order;

import com.app.smartdrive.api.entities.service_orders.ServiceOrderTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoTasksRepository extends JpaRepository<ServiceOrderTasks, Long> {
}
