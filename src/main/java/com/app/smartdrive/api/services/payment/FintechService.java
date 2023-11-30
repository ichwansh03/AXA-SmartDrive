package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.payment.FintechDto;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.services.BaseService;

public interface FintechService extends BaseService<Fintech, Long>{
    Fintech addedFintech(String fint_name, String fint_desc);
    Boolean updateFintech(Long fint_entityid, String fint_name, String fint_desc);
    Boolean deleteFintech(Long fintech_entityid);
    FintechDto getUserFintId(String fint_name);
    
} 
