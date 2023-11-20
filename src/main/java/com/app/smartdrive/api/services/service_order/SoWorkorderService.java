package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

import java.util.Optional;

public interface SoWorkorderService {

    Optional<ServiceOrderWorkorder> findSoWorkorderById(Long sowoId);
}
