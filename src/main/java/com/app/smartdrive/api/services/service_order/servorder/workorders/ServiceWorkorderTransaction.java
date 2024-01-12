package com.app.smartdrive.api.services.service_order.servorder.workorders;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;

import java.util.List;

public interface ServiceWorkorderTransaction {

    List<ServiceOrderWorkorder> addSowoList(List<ServiceOrderTasks> seotList);

    int updateSowoStatus(Boolean sowoStatus, Long sowoId);

    void createWorkorderTask(String sowoName, Long seotId);

    boolean updateTaskByWorkorders(ServiceOrderWorkorder workorder);
}
