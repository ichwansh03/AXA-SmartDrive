package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

import java.util.List;

public interface ServiceWorkorderFactory {

    List<ServiceOrderWorkorder> addSowoList(List<ServiceOrderTasks> seotList);

    int updateSowoStatus(Boolean sowoStatus, Long sowoId);

    void createWorkorderTask(String sowoName, Long seotId);
}
