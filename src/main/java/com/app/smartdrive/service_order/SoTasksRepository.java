package com.app.smartdrive.service_order;

import com.app.smartdrive.service_orders.ServiceOrderTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoTasksRepository extends JpaRepository<ServiceOrderTasks, Long> {
}
