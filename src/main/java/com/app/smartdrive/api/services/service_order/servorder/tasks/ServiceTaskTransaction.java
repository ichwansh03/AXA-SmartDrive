package com.app.smartdrive.api.services.service_order.servorder.tasks;

import com.app.smartdrive.api.dto.service_order.request.SeotPartnerDto;
import com.app.smartdrive.api.dto.service_order.request.ServiceTaskReqDto;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;

import java.lang.reflect.Method;
import java.util.List;

public interface ServiceTaskTransaction {

    List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders);

    List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders);

    List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders);

    int updateTasksStatus(EnumModuleServiceOrders.SeotStatus seotStatus, Long seotId);

    SeotPartnerDto updateSeotPartner(SeotPartnerDto seotPartnerDto, Long seotId);

    void validateWorkorderForPartner(SeotPartnerDto seotPartnerDto, Long seotId);

    List<ServiceTaskReqDto> generateFromTemplateTasks(ServiceOrders serviceOrders, List<TemplateServiceTask> templateServiceTasks,
                                                      EnumModuleServiceOrders.SeotStatus seotStatus, Method taskReflection);

    void notifyCurrentTask(ServiceTaskReqDto serviceOrderTask);
}
