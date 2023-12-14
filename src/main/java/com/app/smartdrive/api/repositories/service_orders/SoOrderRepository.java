package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.dto.service_order.request.ServiceOrderReqDto;
import com.app.smartdrive.api.entities.partner.Partner;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoOrderRepository extends JpaRepository<ServiceOrders, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    ServiceOrders save(ServiceOrders entity);

    List<ServiceOrders> findByServices_ServId(Long servId);


    List<ServiceOrders> findByPartner(Partner partner);

    ServiceOrders findBySeroIdLikeAndServices_ServId(String seroIdLike, Long servId);

    List<ServiceOrders> findByServices_Users_UserEntityId(Long custId);



    @Query(value = "select * from so.service_orders where sero_id like 'PL%' and sero_agent_entityid = ?1", nativeQuery = true)
    ServiceOrders findBySeroIdLikeAndEmployees_EawgEntityid(Long eawgEntityid);

}
