package com.app.smartdrive.api.services.service_order.servorder.orders.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.TasksNotCompletedException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.tasks.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.orders.ServiceOrderTransaction;
import com.app.smartdrive.api.services.service_order.servorder.tasks.ServiceTaskTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceOrderTransactionImpl implements ServiceOrderTransaction {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;
    private final ServOrderTaskService servOrderTaskService;
    private final ServiceTaskTransaction serviceTaskTransaction;
    private final CustomerRequestService customerRequestService;

    SoAdapter soAdapter = new SoAdapter();

    /**
     * Adds a new service order based on the service ID.
     *
     * @param servId the ID of the services
     * @return the newly added service orders
     */
    @Override
    public ServiceOrders addServiceOrders(Long servId) {
        Services services = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + servId + " not found"));
        ServiceOrders orders = new ServiceOrders();

        switch (services.getServType().toString()){
            case "FEASIBILITY" -> {
                orders = generateSeroFeasiblity(services);
                serviceTaskTransaction.addFeasiblityList(orders);
            }
            case "POLIS" -> {
                ServiceOrders previousFs = soOrderRepository.findBySeroIdLikeAndServices_ServId("FS%", services.getServId());
                closeExistingSero(previousFs);
                orders = handlePolisAndClaim(services, null, null, "FS%");
                serviceTaskTransaction.addPolisList(orders);
            }
            case "CLAIM" -> {
                ServiceOrders previousClaim = soOrderRepository.findBySeroIdLikeAndServices_ServId("CL%", services.getServId());
                if (previousClaim != null){
                    closeExistingSero(previousClaim);
                }
                orders = handlePolisAndClaim(services, LocalDateTime.now(), LocalDateTime.now().plusDays(10), "PL%");
                serviceTaskTransaction.addClaimList(orders);
            }
            default -> requestCloseAllSero(services);
        }

        return orders;
    }

    @Transactional
    @Override
    public ServiceOrders generateSeroFeasiblity(Services services){

        ServiceOrders serviceOrders = buildCommonSeroData(services, null, null, null);
        ServiceOrders saved = soOrderRepository.save(serviceOrders);

        log.info("ServOrderTaskImpl::generateSeroFeasiblity successfully added in ID {} ", saved.getSeroId());

        return saved;
    }

    @Transactional
    @Override
    public ServiceOrders handlePolisAndClaim(Services services, LocalDateTime startDate, LocalDateTime endDate, String prefixSeroId){

        ServiceOrders existingSero = soOrderRepository.findBySeroIdLikeAndServices_ServId(prefixSeroId, services.getServId());
        ServiceOrders serviceOrders = buildCommonSeroData(services, existingSero, startDate, endDate);

        ServiceOrders saved = soOrderRepository.save(serviceOrders);
        log.info("ServOrderTaskImpl::generateSeroClaim successfully added {} ", saved.getSeroId());

        return saved;
    }

    /**
     * when tasks is completed, close previous sero
     */
    @Transactional
    @Override
    public void closeExistingSero(ServiceOrders existingSero){
        if (servOrderTaskService.checkAllTaskComplete(existingSero.getSeroId())){
            existingSero.setSeroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED);
            soOrderRepository.save(existingSero);
            log.info("ServOrderImpl::addServiceOrders create new POLIS tasks");
        } else {
            throw new TasksNotCompletedException("Completed your tasks before new request");
        }
    }

    /**
     * when request is TP (CLOSE), close all active sero
     */
    @Transactional
    @Override
    public void requestCloseAllSero(Services services){
        CustomerRequest customerRequest = services.getCustomer();
        this.customerRequestService.changeRequestStatus(customerRequest, EnumCustomer.CreqStatus.CLOSED);

        log.info("ServOrderImpl::requestCloseAllSero change customer request status to CLOSED");

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
                })
                .toList();

        soOrderRepository.saveAll(updateSero);
    }

    @Transactional
    @Override
    public int updateStatusRequest(EnumModuleServiceOrders.SeroStatus seroStatus, String seroReason, String seroId) {
        int requested = soOrderRepository.requestClosePolis(seroStatus, seroReason, seroId);

        if (requested == 0) {
            throw new ValidasiRequestException("Failed to update data", 400);
        }

        return requested;
    }

    @Transactional
    @Override
    public int selectPartner(Partner partner, String seroId) {
        int selected = soOrderRepository.selectPartner(partner, seroId);

        if (selected == 0) {
            throw new ValidasiRequestException("Failed to update data", 400);
        }

        return selected;
    }

    @Override
    public ServiceOrders buildCommonSeroData(Services services, ServiceOrders parentSero, LocalDateTime startDate, LocalDateTime endDate){
        String formatSeroId = soAdapter.formatServiceOrderId(services);

        return ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(EnumModuleServiceOrders.SeroStatus.OPEN)
                .servClaimNo(services.getServInsuranceno())
                .servClaimStartdate(startDate)
                .servClaimEnddate(endDate)
                .parentServiceOrders(parentSero)
                .seroAgentEntityid(services.getCustomer().getEmployeeAreaWorkgroup().getEawgId())
                .employees(services.getCustomer().getEmployeeAreaWorkgroup())
                .services(services).build();

    }
}
