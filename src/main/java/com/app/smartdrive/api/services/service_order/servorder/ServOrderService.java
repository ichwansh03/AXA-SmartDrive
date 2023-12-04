package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;

public interface ServOrderService {

    ServiceOrders addServiceOrders(Long servId) throws NoSuchMethodException;

    ServiceOrders findServiceOrdersById(String seroId);
}
