package com.app.smartdrive.api.services.service_order.premi;

import com.app.smartdrive.api.entities.service_order.ServicePremi;

import java.util.List;

public interface ServPremiService {

    List<ServicePremi> findAllBySemiServId(Long servId);

    ServicePremi addSemi(ServicePremi servicePremi);
}
