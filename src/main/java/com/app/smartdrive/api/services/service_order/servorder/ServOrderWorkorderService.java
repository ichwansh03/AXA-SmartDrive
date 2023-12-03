package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

import java.util.List;

public interface ServOrderWorkorderService {

    List<ServiceOrderWorkorder> addSowoList(List<ServiceOrderTasks> seotList);

    List<ServiceOrderWorkorder> findSowoBySeotId(Long seotId);

    int updateSowoStatus(Boolean sowoStatus, Long sowoId);

    List<ServiceOrderTasks> checkAllWorkComplete(List<ServiceOrderWorkorder> sowoList, ServiceOrderWorkorder sowo);
}
