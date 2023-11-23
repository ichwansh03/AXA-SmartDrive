package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoRepository extends JpaRepository<Services, Long> {

    /*
     * get creq entity id
     *
     */
}
