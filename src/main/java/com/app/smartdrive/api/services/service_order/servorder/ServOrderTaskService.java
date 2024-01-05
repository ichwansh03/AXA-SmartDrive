package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;

import java.util.List;

public interface ServOrderTaskService {

    List<SoTasksDto> findAllTaskByOrderId(String seroId);

    void notifyTask(String mailTo, String subject, String message);

    boolean checkAllTaskComplete(String seroId);
}
