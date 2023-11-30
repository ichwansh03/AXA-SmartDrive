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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderImpl implements ServOrderService {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;

    SoAdapter soAdapter = new SoAdapter();

    @Override
    public ServiceOrders addServiceOrders(Services services) {
        services = soRepository.findById(services.getServId()).get();

        String formatSeroId = soAdapter.formatServiceOrderId(
                services,
                services.getServId(),
                services.getServStartDate());

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

        ServOrderTaskImpl servOrderTask = new ServOrderTaskImpl(soTasksRepository, soWorkorderRepository);
        List<ServiceOrderTasks> serviceOrderTasks = servOrderTask.generateSeotFeasiblity(seroSaved, services);
        seroSaved.setServiceOrderTasks(serviceOrderTasks);

        return serviceOrders;
    }

    @Override
    public ServiceOrders findServiceOrdersById(String seroId) {
        ServiceOrders serviceOrdersById = soOrderRepository.findServiceOrdersById(seroId);
        log.info("SoOrderServiceImpl::findServiceOrdersById in ID {} ",serviceOrdersById);
        return serviceOrdersById;
    }

}
