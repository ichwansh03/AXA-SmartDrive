package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.service_order.request.ServiceOrderReqDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderImpl implements ServOrderService {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;
    private final TestaRepository testaRepository;
    private final TewoRepository tewoRepository;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public ServiceOrders addServiceOrders(Long servId) throws Exception {

        Services services = soRepository.findById(servId).get();

        ServiceOrders orders = generateServiceOrders(services);

        ServiceOrders seroSaved = soOrderRepository.save(orders);
        log.info("SoOrderServiceImpl::addServiceOrders in ID {}",orders.getSeroId());

        ServOrderTaskImpl servOrderTask = new ServOrderTaskImpl(soTasksRepository, soWorkorderRepository, testaRepository, tewoRepository);
        List<ServiceOrderTasks> seotList;

        switch (services.getServType().toString()){
            case "FEASIBLITY" -> seotList = servOrderTask.addFeasiblityList(seroSaved);
            case "POLIS" -> {
                seotList = servOrderTask.addPolisList(seroSaved);
                seroSaved.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE);
            }
            case "CLAIM" -> seotList = servOrderTask.addClaimList(seroSaved);
            default -> {
                seotList = servOrderTask.closeAllTasks(seroSaved);
                seroSaved.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE);
            }
        }

        List<ServiceOrderTasks> serviceOrderTasks = seotList;

        seroSaved.setServiceOrderTasks(serviceOrderTasks);

        return orders;
    }

    @Transactional(readOnly = true)
    @Override
    public ServiceOrders findServiceOrdersById(String seroId) {
        ServiceOrders serviceOrdersById = soOrderRepository.findById(seroId).get();
        log.info("SoOrderServiceImpl::findServiceOrdersById in ID {} ",serviceOrdersById);
        return serviceOrdersById;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrders> findAllSeroByServId(Long servId) {
        List<ServiceOrders> allSeroByServId = soOrderRepository.findByServices_ServId(servId);

        log.info("SoOrderServiceImpl::findAllSeroByServId in ID {} ",allSeroByServId);

        return allSeroByServId;
    }

    @Transactional
    @Override
    public boolean checkAllTaskComplete(String seroId) {

        List<ServiceOrderTasks> seotBySeroId = soTasksRepository.findByServiceOrders_SeroId(seroId);

        List<ServiceOrderWorkorder> sowoBySeotId = soWorkorderRepository.findSowoBySeotId(seotBySeroId.get(0).getSeotId());
        ServOrderWorkorderImpl servOrderWorkorder = new ServOrderWorkorderImpl(soWorkorderRepository, tewoRepository);
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


    @Override
    @Transactional
    public ServiceOrderReqDto updateServiceOrders(ServiceOrderReqDto serviceOrderReqDto, String seroId) throws Exception {
        Optional<ServiceOrders> serviceOrders = soOrderRepository.findById(seroId);

        if (serviceOrders.isPresent() && checkAllTaskComplete(seroId)){
            String formatSeroId = soAdapter.formatServiceOrderId(serviceOrders.get().getServices());
            ServiceOrders newServOrders = ServiceOrders.builder()
                    .seroId(formatSeroId)
                    .seroOrdtType(serviceOrderReqDto.getSeroOrdtType())
                    .seroStatus(serviceOrderReqDto.getSeroStatus())
                    .seroReason(serviceOrderReqDto.getSeroReason())
                    .servClaimNo(serviceOrderReqDto.getServClaimNo())
                    .servClaimStartdate(serviceOrderReqDto.getServClaimStartdate())
                    .servClaimEnddate(serviceOrderReqDto.getServClaimEnddate())
                    .services(serviceOrders.get().getServices()).build();
            soOrderRepository.save(newServOrders);
        } else {
            throw new EntityNotFoundException("Data for id "+seroId+"is not found or all task are not finished");
        }

        return serviceOrderReqDto;
    }

    private ServiceOrders generateServiceOrders(Services services){
        String formatSeroId = soAdapter.formatServiceOrderId(services);

        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroStatus(serviceOrders.getSeroStatus())
                .seroReason(serviceOrders.getSeroReason())
                .servClaimNo(serviceOrders.getServClaimNo())
                .servClaimStartdate(services.getServStartDate())
                .servClaimEnddate(services.getServStartDate().plusYears(1))
                .services(services).build();

        return serviceOrders;
    }
}
