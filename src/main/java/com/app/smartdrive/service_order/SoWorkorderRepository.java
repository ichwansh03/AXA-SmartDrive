package com.app.smartdrive.service_order;

import com.app.smartdrive.service_orders.ServiceOrderWorkorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoWorkorderRepository extends JpaRepository<ServiceOrderWorkorder, Long> {
}
