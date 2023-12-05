package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServImpl implements ServService {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;
    private final CustomerRequestRepository customerRequestRepository;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public Services addService(Long creqId) {

        CustomerRequest cr = customerRequestRepository.findById(creqId).get();

        Services serv;

        if (cr.getCreqType().toString().equals("FEASIBLITY")){
            serv = generateFeasiblity(cr);
        } else if (cr.getCreqType().toString().equals("POLIS") || cr.getCreqType().toString().equals("CLAIM")) {
            serv = generatePolisAndClaim(cr);
        } else {
            serv = generateTypeInactive(cr);
        }

        Services saved = soRepository.save(serv);
        log.info("ServOrderServiceImpl::addService created service");

        ServOrderImpl servOrder = new ServOrderImpl(soRepository, soOrderRepository, soTasksRepository, soWorkorderRepository);
        servOrder.addServiceOrders(saved.getServId());

        log.info("ServOrderServiceImpl::addService created Service Orders");
        soRepository.flush();

        return saved;
    }

    @Override
    public boolean checkAllTaskComplete(String seroId) {

        List<ServiceOrderTasks> seotBySeroId = soTasksRepository.findSeotBySeroId(seroId);

        List<ServiceOrderWorkorder> sowoBySeotId = soWorkorderRepository.findSowoBySeotId(seotBySeroId.get(0).getSeotId());
        ServOrderWorkorderImpl servOrderWorkorder = new ServOrderWorkorderImpl(soWorkorderRepository);
        boolean allWorkComplete = servOrderWorkorder.checkAllWorkComplete(sowoBySeotId);

        boolean checkedAll = false;
        for (ServiceOrderTasks item : seotBySeroId) {
            if (item.getSeotStatus().toString().equals("COMPLETED") && allWorkComplete){
                checkedAll = true;
            }
        }

        log.info("ServOrderTaskImpl::checkAllTaskComplete the results is {}",checkedAll);
        return checkedAll;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Services> findServicesById(Long servId) {
        Optional<Services> byId = soRepository.findById(servId);
        log.info("SoOrderServiceImpl::findServicesById in ID {} ",byId);
        return byId;
    }

    public Services generateFeasiblity(CustomerRequest cr){
        return Services.builder()
                .servType(cr.getCreqType())
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servCreatedOn(cr.getCreqCreateDate())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(7))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .users(cr.getCustomer())
                .customer(cr)
                .build();
    }

    public Services generatePolisAndClaim(CustomerRequest cr){

        return Services.builder()
                .servType(cr.getCreqType())
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                //insurance number still null
                .servInsuranceNo(soAdapter.generatePolisNumber(cr.getServices().getServiceOrdersSet().get(0)))
                .servCreatedOn(cr.getCreqCreateDate())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(7))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .users(cr.getCustomer())
                .customer(cr)
                //this result is null, how to generate this parent services automatically after execute FS
                //.parentServices(generateFeasiblity(cr))
                .build();
    }

    public Services generateTypeInactive(CustomerRequest cr){
        return Services.builder()
                .servType(cr.getCreqType())
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servInsuranceNo(soAdapter.generatePolisNumber(cr.getServices().getServiceOrdersSet().get(0)))
                .servCreatedOn(cr.getCreqCreateDate())
                .servStatus(EnumModuleServiceOrders.ServStatus.INACTIVE)
                .users(cr.getCustomer())
                .customer(cr)
                .parentServices(generateFeasiblity(cr))
                .build();
    }
}
