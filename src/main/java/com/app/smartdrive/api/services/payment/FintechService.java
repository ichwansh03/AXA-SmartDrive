package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.payment.FintechDto;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.services.BaseService;

public interface FintechService extends BaseService<Fintech, Long>{

    List<FintechDto> findAllFintech();
    List<FintechDto> findFintechById(Long fintech_entityid);
    FintechDto addFintech(FintechDto fintechDto);
    Boolean updateFintech(Long fint_entityid, FintechDto fintechDto);
    Boolean deleteFintech(Long fintech_entityid);
    
} 
