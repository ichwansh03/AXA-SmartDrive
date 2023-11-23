package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SoRepository extends JpaRepository<Services, Long> {

    /*
     * SELECT DISTINCT cr.* FROM customer.customer_request cr, so.services serv WHERE cr.creq_entityid = 3
     */
    @Transactional
    @Query("SELECT DISTINCT cr FROM CustomerRequest cr, Services serv WHERE cr.creqEntityId = :id")
    CustomerRequest findByCreqEntityid(@Param("id") Long id);
}
