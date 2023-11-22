package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.stream.Stream;

@Repository
public interface SoWorkorderRepository extends JpaRepository<ServiceOrderWorkorder, Long> {
    Stream<ServiceOrderWorkorder> findAllBySowoSeotId(Long seotId);
}
