package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

import java.util.List;

public interface ServOrderWorkorderService {

    List<ServiceOrderWorkorder> generateSowo(List<ServiceOrderTasks> seotList);

    ServiceOrderWorkorder findBySowoId(Long sowoId);
}
