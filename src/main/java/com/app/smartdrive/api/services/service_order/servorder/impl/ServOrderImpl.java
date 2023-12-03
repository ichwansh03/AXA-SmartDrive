package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderImpl implements ServOrderService {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository workorderRepository;
    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public ServiceOrders addServiceOrders(Long servId) {

        Services services = soRepository.findById(servId).get();

        String formatSeroId = soAdapter.formatServiceOrderId(services);

        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(serviceOrders.getSeroOrdtType())
                .seroStatus(serviceOrders.getSeroStatus())
                .seroReason(serviceOrders.getSeroReason())
                .servClaimNo(serviceOrders.getServClaimNo())
                .servClaimStartdate(services.getServStartDate())
                .servClaimEnddate(services.getServStartDate().plusYears(1))
                .services(services).build();

        ServiceOrders seroSaved = soOrderRepository.save(serviceOrders);
        log.info("SoOrderServiceImpl::addServiceOrders in ID {}",formatSeroId);

        ServOrderTaskImpl servOrderTask = new ServOrderTaskImpl(soTasksRepository, workorderRepository);
        List<ServiceOrderTasks> seotList;

        switch (services.getServType().toString()){
            case "FEASIBLITY" -> seotList = servOrderTask.addFeasiblityList(seroSaved, services);
            case "POLIS" -> seotList = servOrderTask.addPolisList(seroSaved, services);
            case "CLAIM" -> seotList = servOrderTask.addClaimList(seroSaved, services);
            default -> seotList = servOrderTask.closeAllTasks(seroSaved, services);
        }

        List<ServiceOrderTasks> serviceOrderTasks = seotList;

        seroSaved.setServiceOrderTasks(serviceOrderTasks);

        return serviceOrders;
    }

    @Transactional(readOnly = true)
    @Override
    public ServiceOrders findServiceOrdersById(String seroId) {
        ServiceOrders serviceOrdersById = soOrderRepository.findById(seroId).get();
        log.info("SoOrderServiceImpl::findServiceOrdersById in ID {} ",serviceOrdersById);
        return serviceOrdersById;
    }

}
