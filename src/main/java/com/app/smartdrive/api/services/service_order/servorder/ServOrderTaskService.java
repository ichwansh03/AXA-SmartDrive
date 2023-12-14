package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.request.SeotPartnerDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;

import java.util.List;

public interface ServOrderTaskService {

    List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders);

    List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders) throws Exception;

    List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders);

    List<ServiceOrderTasks> findSeotBySeroId(String seroId);

    int updateTasksStatus(EnumModuleServiceOrders.SeotStatus seotStatus, Long seotId);

    SeotPartnerDto updateSeotPartner(SeotPartnerDto seotPartnerDto, Long seotId);

    List<Partner> findAllPartner(String seroId);
}
