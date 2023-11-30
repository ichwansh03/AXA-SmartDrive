package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

public interface ServOrderWorkorderService {

    ServiceOrderWorkorder addSoWorkorder(ServiceOrderWorkorder serviceOrderWorkorder);

    ServiceOrderWorkorder findBySowoId(Long sowoId);
}
