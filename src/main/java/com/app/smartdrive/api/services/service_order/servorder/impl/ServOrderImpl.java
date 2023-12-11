package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.TasksNotCompletedException;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public ServiceOrders addServiceOrders(Long servId) throws Exception {

        Services services = soRepository.findById(servId).get();
        ServiceOrders orders;
        ServOrderTaskImpl servOrderTask = new ServOrderTaskImpl(soTasksRepository, soWorkorderRepository, testaRepository, tewoRepository);

        switch (services.getServType().toString()){
            case "FEASIBLITY" -> {
                orders = generateSeroFeasiblity(services);
                servOrderTask.addFeasiblityList(orders);
                log.info("ServOrderImpl::addServiceOrders create FEASIBLITY tasks");
            }
            case "POLIS" -> {
                orders = generateSeroPolis(services);
                ServiceOrders fs = soOrderRepository.findBySeroIdLikeAndServices_ServId("FS%", services.getServId());

                if (checkAllTaskComplete(fs.getSeroId())){
                    fs.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE);
                    fs.setSeroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED);
                    soOrderRepository.save(fs);
                    servOrderTask.addPolisList(orders);
                    log.info("ServOrderImpl::addServiceOrders create new POLIS tasks");
                } else {
                    throw new TasksNotCompletedException("Completed your feasiblity tasks before new request");
                }

            }
            case "CLAIM" -> {
                orders = generateSeroClaim(services);
                servOrderTask.addClaimList(orders);
                log.info("ServOrderImpl::addServiceOrders create new CLAIM tasks");
            }
            default -> orders = generateSeroClose(services);
        }

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

    @Override
    public List<ServiceOrders> findAllSeroByUserId(Long custId) {
        return soOrderRepository.findByServices_Users_UserEntityId(custId);
    }

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

    @Transactional
    private ServiceOrders generateSeroFeasiblity(Services services){
        String formatSeroId = soAdapter.formatServiceOrderId(services);

        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(serviceOrders.getSeroStatus())
                .seroAgentEntityid(services.getCustomer().getEmployeeAreaWorkgroup().getEawgId())
                .employees(services.getCustomer().getEmployeeAreaWorkgroup())
                .services(services).build();

        ServiceOrders saved = soOrderRepository.save(serviceOrders);

        log.info("ServOrderTaskImpl::generateSeroFeasiblity successfully added {} ", saved.getSeroId());

        return saved;
    }

    @Transactional
    private ServiceOrders generateSeroPolis(Services services){
        String formatSeroId = soAdapter.formatServiceOrderId(services);
        ServiceOrders fs = soOrderRepository.findBySeroIdLikeAndServices_ServId("FS%", services.getServId());
        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(serviceOrders.getSeroStatus())
                .parentServiceOrders(fs)
                .seroAgentEntityid(services.getCustomer().getEmployeeAreaWorkgroup().getEawgId())
                .employees(services.getCustomer().getEmployeeAreaWorkgroup())
                .services(services).build();

        ServiceOrders saved = soOrderRepository.save(serviceOrders);

        log.info("ServOrderTaskImpl::generateSeroPolis successfully added {} ", saved.getSeroId());

        return saved;
    }

    @Transactional
    private ServiceOrders generateSeroClaim(Services services){
        String formatSeroId = soAdapter.formatServiceOrderId(services);
        ServiceOrders pl = soOrderRepository.findBySeroIdLikeAndServices_ServId("PL%", services.getServId());
        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(serviceOrders.getSeroStatus())
                .servClaimStartdate(LocalDateTime.now())
                .servClaimEnddate(LocalDateTime.now().plusDays(10))
                .parentServiceOrders(pl)
                .seroAgentEntityid(services.getCustomer().getEmployeeAreaWorkgroup().getEawgId())
                .employees(services.getCustomer().getEmployeeAreaWorkgroup())
                .services(services).build();

        ServiceOrders saved = soOrderRepository.save(serviceOrders);

        log.info("ServOrderTaskImpl::generateSeroClaim successfully added {} ", saved.getSeroId());

        return saved;
    }

    @Transactional
    private ServiceOrders generateSeroClose(Services services){
        String formatSeroId = soAdapter.formatServiceOrderId(services);
        ServiceOrders pl = soOrderRepository.findBySeroIdLikeAndServices_ServId("PL%", services.getServId());
        ServiceOrders serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE)
                .seroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED)
                .parentServiceOrders(pl)
                .services(services).build();

        ServiceOrders saved = soOrderRepository.save(serviceOrders);

        log.info("ServOrderTaskImpl::generateSeroClose successfully added {} ", saved.getSeroId());

        return saved;
    }
}
