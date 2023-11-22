package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.dto.service_order.ServicesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SoOrderRepository extends JpaRepository<ServiceOrders, String> {

    @Transactional
    @Query("SELECT new com.app.smartdrive.api.dto.service_order.ServicesDto(sero.seroId, serv.servType, " +
            "serv.servInsuranceNo, serv.servCreatedOn, " +
            "serv.servStatus, empl.empName, usr.userName) " +
            "FROM ServiceOrders sero JOIN Services serv ON sero.seroServId = serv.servId " +
            "JOIN Employees empl ON sero.seroAgentEntityid = empl.empEntityid " +
            "JOIN User usr ON serv.servCustEntityid = usr.userEntityId " +
            "WHERE sero.seroId = :seroId")
    ServicesDto findByIdWithServicesAndServiceOrdersAndEmployeesAndUser(@Param("seroId") String seroId);
}
