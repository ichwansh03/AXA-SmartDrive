package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.dto.service_order.ServicesDto;


public interface SoOrderService {

    ServicesDto findDtoById(String seroId);

    ServiceOrders addServiceOrders(ServiceOrders serviceOrders, String seroId);
}
