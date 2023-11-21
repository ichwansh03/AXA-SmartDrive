package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.services.BaseService;

import java.util.List;
import java.util.Optional;

public interface SoWorkorderService {
    List<ServiceOrderWorkorder> findAllBySowoSeotId(Long seotId);
}
