package com.app.smartdrive.api.services.service_order.premi;

import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;

import java.util.List;

public interface ServPremiService {

    ServicePremi findByServId(Long servId);

    ServicePremi addSemi(ServicePremi servicePremi, Long servId);

    ServicePremi generateServPremi(Services services);

    int updateSemiStatus(String semiStatus, Long semiServId);
}
