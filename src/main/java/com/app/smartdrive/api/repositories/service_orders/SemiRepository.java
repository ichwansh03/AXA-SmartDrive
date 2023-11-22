package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServicePremi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemiRepository extends JpaRepository<ServicePremi, Long> {

    List<ServicePremi> findAllBySemiServId(Long servId);
}
