package com.app.smartdrive.api.repositories.service_order;

import com.app.smartdrive.api.entities.service_orders.ServiceOrderWorkorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoWorkorderRepository extends JpaRepository<ServiceOrderWorkorder, Long> {
}
