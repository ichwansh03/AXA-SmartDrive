package com.app.smartdrive.api.services.service_order.premi;

import com.app.smartdrive.api.dto.service_order.request.SecrReqDto;
import com.app.smartdrive.api.dto.service_order.response.SecrDto;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;

import java.util.List;

public interface ServPremiCreditService {

    List<SecrDto> findPremiCreditByServId(Long servId);

    List<ServicePremiCredit> addSecr(ServicePremi servicePremi);

    boolean updateSecr(SecrReqDto secrReqDto, Long secrId, Long secrServId);

}
