package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.TasksNotCompletedException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.servorder.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderImpl implements ServOrderService {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;

    private final ServiceOrderFactory serviceOrderFactory;
    private final ServiceTasksFactory serviceTasksFactory;
    private final ServOrderTaskService servOrderTaskService;

    private final CustomerRequestService customerRequestService;

    @Transactional
    @Override
    public ServiceOrders addServiceOrders(Long servId) throws Exception {

        Services services = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("addServiceOrders(Long servId)::servId "+servId+ "is not found"));
        ServiceOrders orders = new ServiceOrders();

        switch (services.getServType().toString()){
            case "FEASIBLITY" -> {
                orders = serviceOrderFactory.generateSeroFeasiblity(services);
                serviceTasksFactory.addFeasiblityList(orders);
                log.info("ServOrderImpl::addServiceOrders create FEASIBLITY tasks");
            }
            case "POLIS" -> {
                //get previous service order (FS)
                ServiceOrders fs = soOrderRepository.findBySeroIdLikeAndServices_ServId("FS%", services.getServId());
                //close previous service order
                closeExistingSero(fs);
                orders = serviceOrderFactory.handlePolisAndClaim(services, null, null, "FS%");
                serviceTasksFactory.addPolisList(orders);
                log.info("ServOrderImpl::addServiceOrders create POLIS tasks");
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
                orders = serviceOrderFactory.handlePolisAndClaim(services, LocalDateTime.now(), LocalDateTime.now().plusDays(10), "PL%");
                serviceTasksFactory.addClaimList(orders);
                log.info("ServOrderImpl::addServiceOrders create CLAIM tasks");
            }
            default -> requestCloseAllSero(services);
        }

        return orders;
    }

    @Transactional(readOnly = true)
    @Override
    public ServiceOrders findServiceOrdersById(String seroId) {
        ServiceOrders serviceOrdersById = soOrderRepository.findById(seroId)
                .orElseThrow(() -> new EntityNotFoundException("ID "+seroId+" is not found"));
        log.info("SoOrderServiceImpl::findServiceOrdersById in ID {} ",serviceOrdersById);
        return serviceOrdersById;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrders> findAllSeroByServId(Long servId) {
        List<ServiceOrders> allSeroByServId = soOrderRepository.findByServices_ServId(servId);

        if (allSeroByServId.isEmpty()){
            throw new EntityNotFoundException("findAllSeroByServId(Long servId)::Service ID is not found");
        }

        log.info("SoOrderServiceImpl::findAllSeroByServId from service ID {} ",servId);

        return allSeroByServId;
    }

    @Override
    public List<ServiceOrders> findAllSeroByUserId(Long custId) {
        List<ServiceOrders> entityId = soOrderRepository.findByServices_Users_UserEntityId(custId);

        if (entityId.isEmpty()) {
            throw new EntityNotFoundException("findAllSeroByUserId(Long custId)::custId is not found");
        }

        log.info("SoOrderServiceImpl::findAllSeroByServId from user ID {} ",custId);

        return entityId;
    }

    @Override
    public Page<ServiceOrderRespDto> pageServiceOrderByUserId(Pageable pageable, String seroOrdtType, String seroStatus) {

        EnumModuleServiceOrders.SeroStatus status = EnumModuleServiceOrders.SeroStatus.valueOf(seroStatus);
        Page<ServiceOrders> serviceOrdersPage;

        if (Objects.equals(seroOrdtType, "ALL")) {
            serviceOrdersPage = soOrderRepository.findAll(pageable);
        } else {
            EnumModuleServiceOrders.SeroOrdtType type = EnumModuleServiceOrders.SeroOrdtType.valueOf(seroOrdtType);
            serviceOrdersPage = soOrderRepository.findBySeroOrdtTypeAndSeroStatus(pageable, type, status);
        }

        return serviceOrdersPage.map(serviceOrders -> TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class));
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
    public int updateStatusRequest(EnumModuleServiceOrders.SeroStatus seroStatus, String seroReason, String seroId) {
        int requested = soOrderRepository.requestClosePolis(seroStatus, seroReason, seroId);

        if (requested == 0) {
            throw new ValidasiRequestException("Failed to update data", 400);
        }

        return requested;
    }

    /**
     * when request is TP (CLOSE), close all active sero
     */
    @Transactional
    private void requestCloseAllSero(Services services){
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

    /**
     * when tasks is completed, close previous sero
     */
    private void closeExistingSero(ServiceOrders existingSero){
        if (servOrderTaskService.checkAllTaskComplete(existingSero.getSeroId())){
            existingSero.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CLOSE);
            existingSero.setSeroStatus(EnumModuleServiceOrders.SeroStatus.CLOSED);
            soOrderRepository.save(existingSero);
            log.info("ServOrderImpl::addServiceOrders create new POLIS tasks");
        } else {
            throw new TasksNotCompletedException("Completed your tasks before new request");
        }
    }
}
