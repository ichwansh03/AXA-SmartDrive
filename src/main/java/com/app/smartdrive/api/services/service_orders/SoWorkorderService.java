package com.app.smartdrive.api.services.service_orders;

import com.app.smartdrive.api.entities.service_orders.ServiceOrderWorkorder;

import java.util.Optional;

public interface SoWorkorderService {

    Optional<ServiceOrderWorkorder> findSoWorkorderById(Long sowoId);
}
