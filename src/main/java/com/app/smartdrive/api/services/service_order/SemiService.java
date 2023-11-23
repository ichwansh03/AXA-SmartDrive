package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ServicePremi;

import java.util.List;

public interface SemiService {

    List<ServicePremi> findAllBySemiServId(Long servId);
}
