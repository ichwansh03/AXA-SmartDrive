package com.app.smartdrive.api.services.service_order.premi;

import com.app.smartdrive.api.entities.service_order.ServicePremi;

import java.util.List;

public interface ServPremiService {

    List<ServicePremi> findByServId(Long servId);

    ServicePremi addSemi(ServicePremi servicePremi, Long servId);

    int updateSemiStatus(String semiStatus, Long semiServId);
}
