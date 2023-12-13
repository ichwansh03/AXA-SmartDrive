package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;

import java.util.List;

public interface ServOrderService {

    ServiceOrders addServiceOrders(Long servId) throws Exception;

    ServiceOrders findServiceOrdersById(String seroId);

    List<ServiceOrders> findAllSeroByServId(Long servId);

    List<ServiceOrders> findAllSeroByUserId(Long custId);

    List<Partner> findAllPartner(String seroId);
}
