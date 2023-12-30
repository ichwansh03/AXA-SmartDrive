package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;

import java.util.List;

public interface ServOrderTaskService {

    List<ServiceOrderTasks> findSeotBySeroId(String seroId);

    void notifyTask(String mailTo, String subject, String message);

    boolean checkAllTaskComplete(String seroId);
}
