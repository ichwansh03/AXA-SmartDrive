package com.app.smartdrive.service_order;

import com.app.smartdrive.service_orders.ServiceOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoOrderRepository extends JpaRepository<ServiceOrders, String> {
}
