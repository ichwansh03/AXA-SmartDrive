package com.app.smartdrive.api.services.service_orders;

import com.app.smartdrive.api.entities.service_orders.ServiceOrders;

import java.util.Optional;

public interface SoService {

    Optional<ServiceOrders> findSoById(String seroId);
}
