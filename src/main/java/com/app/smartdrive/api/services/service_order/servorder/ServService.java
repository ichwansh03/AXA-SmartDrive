package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.request.ServiceReqDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;

import java.util.Optional;

public interface ServService {

    Services addService(Long creqId) throws Exception;

    Optional<Services> findServicesById(Long servId);

    Services updateService(Long servId, Services services) throws Exception;
}
