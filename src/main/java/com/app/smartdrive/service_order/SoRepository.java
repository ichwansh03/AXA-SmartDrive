package com.app.smartdrive.service_order;

import com.app.smartdrive.service_orders.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoRepository extends JpaRepository<Services, Long> {
}
