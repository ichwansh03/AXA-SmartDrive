package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.dto.service_order.response.ServSeroDto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SoOrderRepository extends JpaRepository<ServiceOrders, String> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Transactional
//    @Query("SELECT new com.app.smartdrive.api.dto.service_order.response.ServSeroDto(sero.seroId, sero.services.servType, " +
//            "sero.services.servCreatedOn, sero.seroStatus) " +
//            "FROM ServiceOrders sero JOIN sero.services " +
//            "WHERE sero.seroId = :seroId")
//    ServSeroDto findByIdServicesDto(@Param("seroId") String seroId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ServiceOrders findServiceOrdersById(@Param("seroId") String seroId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServiceOrders save(ServiceOrders entity);
}
