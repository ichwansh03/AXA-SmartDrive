package com.smartdrive.serviceorderservice.services.servorder;


import com.smartdrive.serviceorderservice.dto.request.SeotPartnerDto;
import com.smartdrive.serviceorderservice.entities.ServiceOrderTasks;
import com.smartdrive.serviceorderservice.entities.ServiceOrders;
import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;

import java.util.List;

public interface ServOrderTaskService {

    List<ServiceOrderTasks> findSeotBySeroId(String seroId);

    int updateTasksStatus(EnumModuleServiceOrders.SeotStatus seotStatus, Long seotId);

    SeotPartnerDto updateSeotPartner(SeotPartnerDto seotPartnerDto, Long seotId);

    //void notifyTask(EmailReq emailReq, ServiceOrders serviceOrders, String subject, String message);

    boolean checkAllTaskComplete(String seroId);
}
