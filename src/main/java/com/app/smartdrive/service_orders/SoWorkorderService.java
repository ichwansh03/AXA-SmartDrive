package com.app.smartdrive.service_orders;

import com.app.smartdrive.service_orders.ServiceOrderWorkorder;

import java.util.Optional;

public interface SoWorkorderService {

    Optional<ServiceOrderWorkorder> findSoWorkorderById(Long sowoId);
}
