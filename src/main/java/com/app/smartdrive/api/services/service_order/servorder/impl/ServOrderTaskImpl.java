package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskImpl implements ServOrderTaskService {

    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;

    @Override
    public List<ServiceOrderTasks> generateSeotFeasiblity(ServiceOrders serviceOrders, Services services) {

        List<ServiceOrderTasks> seot = new ArrayList<>();
        seot.add(new ServiceOrderTasks("REVIEW & CHECK CUSTOMER REQUEST", services.getServStartDate(), services.getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS.toString(), 1, serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("PROSPEK CUSTOMER POTENTIAL", services.getServStartDate().plusDays(2), services.getServStartDate().plusDays(3), EnumModuleServiceOrders.SeotStatus.INPROGRESS.toString(), 2, serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("PREMI SCHEMA", services.getServStartDate().plusDays(4), services.getServStartDate().plusDays(5), EnumModuleServiceOrders.SeotStatus.INPROGRESS.toString(), 3, serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("LEGAL DOCUMENT SIGNED", services.getServStartDate().plusDays(6), services.getServStartDate().plusDays(7), EnumModuleServiceOrders.SeotStatus.INPROGRESS.toString(), 4, serviceOrders.getAreaWorkGroup(), serviceOrders));

        List<ServiceOrderTasks> tasksList = soTasksRepository.saveAll(seot);

//        ServiceOrderTasks task = new ServiceOrderTasks();
//
//        ServOrderWorkorderImpl servOrderWorkorder = new ServOrderWorkorderImpl(soWorkorderRepository);
//
//        List<ServiceOrderWorkorder> workorders = servOrderWorkorder.generateSowo(task);
//        task.setServiceOrderWorkorders(workorders);

        return tasksList;
    }

    @Override
    public ServiceOrderTasks findSeotById(Long seotId) {
        ServiceOrderTasks seotById = soTasksRepository.findSeotById(seotId);
        log.info("SoOrderServiceImpl::findSeotById in ID {} ",seotById);
        return seotById;
    }

}
