package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.ServOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderServiceImpl implements ServOrderService {

    private final SoRepository soRepository;

    private final SoOrderRepository soOrderRepository;

    private final SoTasksRepository soTasksRepository;

    private final SoWorkorderRepository soWorkorderRepository;

    @Override
    public Services addServices(CustomerRequest customerRequest, CustomerInscAssets customerInscAssets, User user, Long creqId) {
        Services services = Services.builder()
                .servCreatedOn(customerRequest.getCreqCreateDate())
                .servType(customerRequest.getCreqType())
                .servVehicleNumber(customerInscAssets.getCiasPoliceNumber())
                .servStartDate(customerInscAssets.getCiasStartdate())
                .servEndDate(customerInscAssets.getCiasEnddate())
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .customer(customerRequest)
                .users(user)
                .build();

        Services saveService = soRepository.save(services);

        services.setParentServices(services);

        soRepository.flush();

        log.info("SoServiceImpl::addServices() successfully added {}",services);

        return saveService;
    }

    @Override
    public Services findServicesById(Long servId) {
        Services servicesById = soRepository.findServicesById(servId);
        log.info("SoOrderServiceImpl::findServicesById in ID {} ",servicesById);
        return servicesById;
    }

    @Override
    public ServiceOrders addServiceOrders(ServiceOrders serviceOrders) {
        Services services = soRepository.findById(2L).get();

        SoAdapter soAdapter = new SoAdapter();
        String formatSeroId = soAdapter.formatServiceOrderId(services, services.getUsers().getUserEntityId(), services.getServStartDate());

        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(serviceOrders.getSeroOrdtType())
                .seroStatus(serviceOrders.getSeroStatus())
                .seroReason(serviceOrders.getSeroReason())
                .servClaimNo(serviceOrders.getServClaimNo())
                .servClaimStartdate(services.getServStartDate())
                .servClaimEnddate(services.getServStartDate().plusYears(1))
                .services(services).build();

        log.info("SoOrderServiceImpl::addServiceOrders in ID {}",formatSeroId);
        return soOrderRepository.save(serviceOrders);
    }

    @Override
    public ServiceOrders findServiceOrdersById(String seroId) {
        ServiceOrders serviceOrdersById = soOrderRepository.findServiceOrdersById(seroId);
        log.info("SoOrderServiceImpl::findServiceOrdersById in ID {} ",serviceOrdersById);
        return serviceOrdersById;
    }

    @Override
    public ServiceOrderTasks addServiceOrderTasks(ServiceOrderTasks serviceOrderTasks) {
        ServiceOrders serviceOrders = soOrderRepository.findById("PL0001-20231126").get();

        String statusCompleted = (serviceOrders.getServClaimEnddate().isAfter(LocalDateTime.now())) ? "INPROGRESS" : "COMPLETED";

        serviceOrderTasks = ServiceOrderTasks.builder()
                .seotName(serviceOrderTasks.getSeotName())
                .serviceOrders(serviceOrders)
                .seotStatus(statusCompleted).build();

        log.info("SoOrderServiceImpl::addServiceOrderTasks created {} ",serviceOrderTasks);
        return soTasksRepository.save(serviceOrderTasks);
    }

    @Override
    public ServiceOrderTasks findSeotById(Long seotId) {
        ServiceOrderTasks seotById = soTasksRepository.findSeotById(seotId);
        log.info("SoOrderServiceImpl::findSeotById in ID {} ",seotById);
        return seotById;
    }

    @Override
    public ServiceOrderWorkorder addSoWorkorder(ServiceOrderWorkorder serviceOrderWorkorder) {

        serviceOrderWorkorder = ServiceOrderWorkorder.builder()
                    .sowoName(serviceOrderWorkorder.getSowoName())
                    .sowoModDate(LocalDateTime.now())
                    .sowoStatus(serviceOrderWorkorder.getSowoStatus())
                    .sowoSeotId(serviceOrderWorkorder.getSowoSeotId()).build();

        log.info("SoOrderServiceImpl::addSoWorkorder created {} ", serviceOrderWorkorder);
        return soWorkorderRepository.save(serviceOrderWorkorder);
    }

}
