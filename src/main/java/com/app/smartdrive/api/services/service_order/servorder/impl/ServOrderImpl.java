package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
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

    private SoAdapter soAdapter;

    @Transactional
    @Override
    public ServiceOrders addServiceOrders(Long servId) throws Exception {

        Services services = soRepository.findById(servId).get();

        ServiceOrders orders;

        if (services.getServType().toString().equals("FEASIBLITY")) {
            orders = generateSeroFeasiblity(services);
        } else {
            orders = generateSeroPolis(services);
        }

        ServiceOrders seroSaved = soOrderRepository.save(orders);
        log.info("SoOrderServiceImpl::addServiceOrders in ID {}",orders.getSeroId());

        ServOrderTaskImpl servOrderTask = new ServOrderTaskImpl(soTasksRepository, soWorkorderRepository, testaRepository, tewoRepository);
        List<ServiceOrderTasks> seotList;

        switch (services.getServType().toString()){
            case "FEASIBLITY" -> seotList = servOrderTask.addFeasiblityList(seroSaved);
            case "POLIS" -> seotList = servOrderTask.addPolisList(seroSaved);
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

    private ServiceOrders generateSeroFeasiblity(Services services){
        soAdapter = new SoAdapter();
        String formatSeroId = soAdapter.formatServiceOrderId(services);

        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(serviceOrders.getSeroStatus())
                .services(services).build();

        return serviceOrders;
    }

    private ServiceOrders generateSeroPolis(Services services){
        soAdapter = new SoAdapter();
        String formatSeroId = soAdapter.formatServiceOrderId(services);

        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(serviceOrders.getSeroStatus())
                .servClaimNo(serviceOrders.getServClaimNo())
                .servClaimStartdate(services.getServStartDate())
                .servClaimEnddate(services.getServStartDate().plusYears(1))
                .services(services).build();

        return serviceOrders;
    }
}
