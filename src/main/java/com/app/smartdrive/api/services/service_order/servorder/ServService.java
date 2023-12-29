package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;

public interface ServService {

    ServiceRespDto findServicesById(Long servId);

}
