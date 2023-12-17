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
import java.util.List;

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
                //get previous service order (FS)
                ServiceOrders fs = soOrderRepository.findBySeroIdLikeAndServices_ServId("FS%", services.getServId());
                //close previous service order
                closeExistingSero(fs);

                orders = handlePolisAndClaim(services, null, null, "FS%");
                servOrderTaskService.addPolisList(orders);
            }
            case "CLAIM" -> {
                //get previous service order (CL)
                ServiceOrders cl = soOrderRepository.findBySeroIdLikeAndServices_ServId("CL%", services.getServId());
                //if user second time request claim
                if (cl != null){
                    //close second claim
                    closeExistingSero(cl);
                }
                //first time to request claim
                orders = handlePolisAndClaim(services, LocalDateTime.now(), LocalDateTime.now().plusDays(10), "PL%");
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
    @Override
    public int selectPartner(Partner partner, String seroId) {
        return soOrderRepository.selectPartner(partner, seroId);
    }

    /**
     * when request is TP (CLOSE), close all active sero
     */
    @Transactional
    private ServiceOrders generateSeroClosePolis(Services services){
        //get all service order by servId
        List<ServiceOrders> serviceOrders = soOrderRepository.findByServices_ServId(services.getServId());
        List<ServiceOrders> updateSero = serviceOrders.stream()

                .filter(order -> order.getSeroStatus() == EnumModuleServiceOrders.SeroStatus.OPEN)
                //change data without creating a new copy
                .peek(order -> {
                    order.setSeroId(order.getSeroId());
                    order.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE);
                    order.setSeroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED);
                    order.setSeroReason(order.getServices().getCustomer().getCustomerClaim().getCuclReason());
                    order.setSeroAgentEntityid(order.getSeroAgentEntityid());
                    order.setEmployees(order.getEmployees());
                    order.setServices(order.getServices());
                })
                .toList();

        soOrderRepository.saveAll(updateSero);

        return updateSero.isEmpty() ? null : updateSero.get(2);
    }

    @Transactional
    private ServiceOrders handlePolisAndClaim(Services services, LocalDateTime startDate, LocalDateTime endDate, String prefixSeroId){
        String formatSeroId = soAdapter.formatServiceOrderId(services);
        ServiceOrders existingSero = soOrderRepository.findBySeroIdLikeAndServices_ServId(prefixSeroId, services.getServId());
        ServiceOrders serviceOrders = new ServiceOrders();
        serviceOrders = ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(serviceOrders.getSeroStatus())
                .servClaimNo(services.getServInsuranceNo())
                .servClaimStartdate(startDate)
                .servClaimEnddate(endDate)
                .parentServiceOrders(existingSero)
                .seroAgentEntityid(services.getCustomer().getEmployeeAreaWorkgroup().getEawgId())
                .employees(services.getCustomer().getEmployeeAreaWorkgroup())
                .services(services).build();

        ServiceOrders saved = soOrderRepository.save(serviceOrders);
        log.info("ServOrderTaskImpl::generateSeroClaim successfully added {} ", saved.getSeroId());

        return saved;
    }

    /**
     * when tasks is completed, close previous sero
     */
    private void closeExistingSero(ServiceOrders existingSero){
        if (checkAllTaskComplete(existingSero.getSeroId())){
            existingSero.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE);
            existingSero.setSeroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED);
            soOrderRepository.save(existingSero);
            log.info("ServOrderImpl::addServiceOrders create new POLIS tasks");
        } else {
            throw new TasksNotCompletedException("Completed your tasks before new request");
        }
    }
}
