package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.EmailReq;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;

import java.util.List;

public interface ServOrderTaskService {

    List<ServiceOrderTasks> findSeotBySeroId(String seroId);

    void notifyTask(EmailReq emailReq, ServiceOrders serviceOrders, String subject, String message);

    boolean checkAllTaskComplete(String seroId);
}
