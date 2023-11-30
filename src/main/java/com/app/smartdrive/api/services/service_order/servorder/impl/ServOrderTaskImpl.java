package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskImpl implements ServOrderTaskService {

    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;

    @Override
    public ServiceOrderTasks addServiceOrderTasks(ServiceOrderTasks serviceOrderTasks) {
        ServiceOrders serviceOrders = soOrderRepository.findById("PL0001-20231126").get();

        serviceOrderTasks = ServiceOrderTasks.builder()
                .seotName(serviceOrderTasks.getSeotName())
                .serviceOrders(serviceOrders)
                .seotStatus(serviceOrderTasks.getSeotStatus()).build();

        log.info("SoOrderServiceImpl::addServiceOrderTasks created {} ",serviceOrderTasks);
        return soTasksRepository.save(serviceOrderTasks);
    }

    @Override
    public ServiceOrderTasks findSeotById(Long seotId) {
        ServiceOrderTasks seotById = soTasksRepository.findSeotById(seotId);
        log.info("SoOrderServiceImpl::findSeotById in ID {} ",seotById);
        return seotById;
    }

}
