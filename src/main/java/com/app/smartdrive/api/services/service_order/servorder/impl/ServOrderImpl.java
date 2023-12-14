package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.TasksNotCompletedException;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderImpl implements ServOrderService {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;

    private final ServOrderTaskService servOrderTaskService;
    private final ServOrderWorkorderService servOrderWorkorderService;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public ServiceOrders addServiceOrders(Long servId) throws Exception {

        Services services = soRepository.findById(servId).get();
        ServiceOrders orders;

        switch (services.getServType().toString()){
            case "FEASIBLITY" -> {
                orders = generateSeroFeasiblity(services);
                servOrderTaskService.addFeasiblityList(orders);
                log.info("ServOrderImpl::addServiceOrders create FEASIBLITY tasks");
            }
            case "POLIS" -> {
                orders = generateSeroPolis(services);
                ServiceOrders fs = soOrderRepository.findBySeroIdLikeAndServices_ServId("FS%", services.getServId());

                if (checkAllTaskComplete(fs.getSeroId())){
                    fs.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE);
                    fs.setSeroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED);
                    soOrderRepository.save(fs);
                    servOrderTaskService.addPolisList(orders);
                    log.info("ServOrderImpl::addServiceOrders create new POLIS tasks");
                } else {
                    throw new TasksNotCompletedException("Completed your feasiblity tasks before new request");
                }

            }
            case "CLAIM" -> {
                ServiceOrders cl = soOrderRepository.findBySeroIdLikeAndServices_ServId("CL%", services.getServId());
                if (cl != null){
                    if (checkAllTaskComplete(cl.getSeroId())){
                        cl.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE);
                        cl.setSeroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED);
                        soOrderRepository.save(cl);
                    }
                    else {
                        throw new TasksNotCompletedException("Completed your another claim tasks before new request");
                    }
                    orders = generateSeroClaim(services);
                    servOrderTaskService.addClaimList(orders);
                    log.info("ServOrderImpl::addServiceOrders create new CLAIM tasks");
                    break;
                }
                orders = generateSeroClaim(services);
                servOrderTaskService.addClaimList(orders);
            }
            default -> orders = generateSeroClosePolis(services);
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
        boolean allWorkComplete = servOrderWorkorderService.checkAllWorkComplete(sowoBySeotId);

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
    @Override
    public ServiceOrders generateSeroFeasiblity(Services services){
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
    @Override
    public ServiceOrders generateSeroPolis(Services services){
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
    @Override
    public ServiceOrders generateSeroClaim(Services services){
        String formatSeroId = soAdapter.formatServiceOrderId(services);
        ServiceOrders pl = soOrderRepository.findBySeroIdLikeAndServices_ServId("PL%", services.getServId());
        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(serviceOrders.getSeroStatus())
                .servClaimNo(services.getServInsuranceNo())
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
    @Override
    public ServiceOrders generateSeroClosePolis(Services services){
        ServiceOrders saved = new ServiceOrders();
        Map<String, ServiceOrders> orderMap = new HashMap<>();
        orderMap.put("FS", soOrderRepository.findBySeroIdLikeAndServices_ServId("FS%", services.getServId()));
        orderMap.put("PL", soOrderRepository.findBySeroIdLikeAndServices_ServId("PL%", services.getServId()));
        orderMap.put("CL", soOrderRepository.findBySeroIdLikeAndServices_ServId("CL%", services.getServId()));

        for (Map.Entry<String, ServiceOrders> entry : orderMap.entrySet()) {
            ServiceOrders order = entry.getValue();
            if (order != null && order.getSeroStatus() == EnumModuleServiceOrders.SeroStatus.OPEN) {
                order.setSeroId(order.getSeroId());
                order.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE);
                order.setSeroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED);
                order.setSeroAgentEntityid(order.getSeroAgentEntityid());
                order.setEmployees(order.getEmployees());
                order.setServices(order.getServices());
                soOrderRepository.save(order);
                log.info("ServOrderTaskImpl::generateSeroClose successfully updated {} to CLOSED", order.getSeroId());
            }
        }
        return saved;
    }

    @Transactional
    @Override
    public int selectPartner(Partner partner, String seroId) {
        return soOrderRepository.selectPartner(partner, seroId);
    }
}
