package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServOrderService {

    ServiceOrders addServiceOrders(Long servId) throws Exception;

    ServiceOrders findServiceOrdersById(String seroId);

    List<ServiceOrders> findAllSeroByServId(Long servId);

    List<ServiceOrders> findAllSeroByUserId(Long custId);

    ServiceOrders generateSeroFeasiblity(Services services);

    ServiceOrders generateSeroPolis(Services services);

    ServiceOrders generateSeroClaim(Services services);

    ServiceOrders generateSeroClosePolis(Services services);

    int selectPartner(Partner partner, String seroId);

}
