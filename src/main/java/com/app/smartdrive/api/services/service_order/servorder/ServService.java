package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.Services;

import java.util.Optional;

public interface ServService {

    Services addService(Long creqId);

    Optional<Services> findServicesById(Long servId);

    boolean checkAllTaskComplete(String seroId);
}
