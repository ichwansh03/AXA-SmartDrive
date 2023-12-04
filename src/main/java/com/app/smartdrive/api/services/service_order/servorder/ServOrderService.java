package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;

import java.util.List;

public interface ServOrderService {

    ServiceOrders addServiceOrders(Long servId) throws NoSuchMethodException;

    ServiceOrders findServiceOrdersById(String seroId);

    List<ServiceOrders> findAllSeroByServId(Long servId);
}
