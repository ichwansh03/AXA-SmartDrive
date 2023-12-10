package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.payment.Request.Fintech.FintechDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechIdForUserDtoResponse;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.services.BaseService;


public interface FintechService extends BaseService<FintechDtoResponse, Long>{

    FintechDtoResponse addFintech(FintechDtoRequests fintechDtoRequests);
    Boolean updateFintech(Long fint_entityid ,FintechDtoRequests fintechDtoRequests);
    Boolean deleteFintech(Long fintech_entityid);
    FintechIdForUserDtoResponse getUserFintId(String fint_name);
    
} 
