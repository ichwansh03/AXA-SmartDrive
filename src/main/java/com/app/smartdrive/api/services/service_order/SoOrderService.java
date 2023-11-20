package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;

import java.util.Optional;

public interface SoOrderService {

    Optional<ServiceOrders> findBySeroId(String seroId);

    ServiceOrders addSero(ServiceOrders serviceOrders);
}
