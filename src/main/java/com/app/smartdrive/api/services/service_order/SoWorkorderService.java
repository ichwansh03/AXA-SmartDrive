package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

import java.util.stream.Stream;

public interface SoWorkorderService {
    Stream<ServiceOrderWorkorder> findAllBySowoSeotId(Long seotId);
}
