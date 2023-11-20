package com.app.smartdrive.service_orders;

import com.app.smartdrive.service_orders.ServiceOrders;

import java.util.Optional;

public interface SoOrderService {

    Optional<ServiceOrders> findBySeroId(String seroId);

    ServiceOrders addSero(ServiceOrders serviceOrders);
}
