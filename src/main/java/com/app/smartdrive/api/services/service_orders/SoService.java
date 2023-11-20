package com.app.smartdrive.api.services.service_orders;

import com.app.smartdrive.api.entities.service_orders.Services;

import java.util.Optional;

public interface SoService {

    Optional<Services> findServicesById(Long servId);

    Services addServices(Services services);
}
