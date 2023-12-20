package com.smartdrive.serviceorderservice.services.servorder;


import com.smartdrive.serviceorderservice.dto.response.ServiceRespDto;
import com.smartdrive.serviceorderservice.entities.Services;

public interface ServService {

    Services addService(Long creqId) throws Exception;

    ServiceRespDto findServicesById(Long servId);

}
