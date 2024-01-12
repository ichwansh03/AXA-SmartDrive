package com.app.smartdrive.api.services.service_order.servorder.tasks;

import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;

import java.util.List;

public interface ServOrderTaskService {

    SoTasksDto findTaskById(Long seotId);

    List<SoTasksDto> findAllTaskByOrderId(String seroId);

    void notifyTask(String mailTo, String subject, String message);

    boolean checkAllTaskComplete(String seroId);
}
