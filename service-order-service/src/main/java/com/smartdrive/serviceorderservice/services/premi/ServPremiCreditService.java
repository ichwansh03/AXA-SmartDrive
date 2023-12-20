package com.smartdrive.serviceorderservice.services.premi;


import com.smartdrive.serviceorderservice.dto.request.SecrReqDto;
import com.smartdrive.serviceorderservice.entities.ServicePremi;
import com.smartdrive.serviceorderservice.entities.ServicePremiCredit;

import java.util.List;

public interface ServPremiCreditService {

    List<ServicePremiCredit> findByServId(Long servId);

    List<ServicePremiCredit> addSecr(ServicePremi servicePremi);

    boolean updateSecr(SecrReqDto secrReqDto, Long secrId, Long secrServId);

}