package com.app.smartdrive.api.services.service_order.premi;

import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;

import java.util.List;

public interface ServPremiCreditService {

    List<ServicePremiCredit> findAllBySecrServId(Long servId);

    List<ServicePremiCredit> addSecr(ServicePremi servicePremi);
}
