package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.Services;

public interface ServService {

    Services addService(Long creqId);

    Services findServicesById(Long servId);




}
