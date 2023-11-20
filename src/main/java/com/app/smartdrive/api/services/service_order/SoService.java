package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.Services;

import java.util.Optional;

public interface SoService {

    Optional<Services> findServicesById(Long servId);

    Services addServices(Services services);
}
