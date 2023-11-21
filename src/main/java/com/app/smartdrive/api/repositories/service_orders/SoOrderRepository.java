package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoOrderRepository extends JpaRepository<ServiceOrders, String> {

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "SELECT new com.app.smartdrive.api.entities.service_order.dto.SoDto(sero.sero_id, serv.serv_type, serv.serv_insuranceno, serv.serv_created_on, serv.serv_status, empl.emp_name, usr.user_name)" +
//            " FROM so.service_orders sero JOIN so.services serv ON sero.sero_serv_id = serv.serv_id" +
//            " JOIN hr.employees empl ON sero.sero_agent_entityid = empl.emp_entityid" +
//            " JOIN users.users usr ON serv.serv_cust_entityid = usr.user_entityid" +
//            " WHERE sero.sero_id = :seroid", nativeQuery = true)
//    SoDto findSeroById(@Param("seroid") String seroid);
}
