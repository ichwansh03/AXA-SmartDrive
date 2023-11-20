package com.app.smartdrive.service_orders;

import com.app.smartdrive.service_orders.Services;

import java.util.Optional;

public interface SoService {

    Optional<Services> findServicesById(Long servId);

    Services addServices(Services services);
}
