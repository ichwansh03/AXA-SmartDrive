package com.app.smartdrive.api.services.service_order.servorder.tasks.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.TasksNotCompletedException;
import com.app.smartdrive.api.dto.service_order.request.SeotPartnerDto;
import com.app.smartdrive.api.dto.service_order.request.ServiceTaskReqDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.orders.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.services.ServService;
import com.app.smartdrive.api.services.service_order.servorder.tasks.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.tasks.ServiceTaskTransaction;
import com.app.smartdrive.api.services.service_order.servorder.workorders.ServOrderWorkorderService;
import com.app.smartdrive.api.services.service_order.servorder.workorders.ServiceWorkorderTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceTaskTransactionImpl implements ServiceTaskTransaction {

    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final TestaRepository testaRepository;
    private final PartnerRepository partnerRepository;

    private final ServiceWorkorderTransaction serviceWorkorderTransaction;
    private final ServService servService;
    private final ServOrderService servOrderService;
    private final ServOrderTaskService servOrderTaskService;
    private final ServOrderWorkorderService servOrderWorkorderService;

    @Transactional
    @Override
    public List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders) {

        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(1L);

        List<ServiceTaskReqDto> seot = generateFromTemplateTasks(serviceOrders, templateServiceTasks, EnumModuleServiceOrders.SeotStatus.INPROGRESS, null);

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);
        List<ServiceOrderTasks> serviceOrderTasks = soTasksRepository.saveAll(mapperTaskList);

        serviceWorkorderTransaction.addSowoList(serviceOrderTasks);

        return serviceOrderTasks;
    }

    @Transactional
    @Override
    public List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders) {

        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(2L);
        Method generatePolisNumber;
        try {
            generatePolisNumber = SoAdapter.class.getMethod("generatePolis", CustomerRequest.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        List<ServiceTaskReqDto> dtos = generateFromTemplateTasks(serviceOrders, templateServiceTasks,
                EnumModuleServiceOrders.SeotStatus.COMPLETED, generatePolisNumber);

        log.info("ServOrderTaskImpl::addPolisList the result of number polis is {} ", generatePolisNumber);

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(dtos, ServiceOrderTasks.class);
        return soTasksRepository.saveAll(mapperTaskList);
    }

    @Transactional
    @Override
    public List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders) {

        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(3L);

        List<ServiceTaskReqDto> dtoList = generateFromTemplateTasks(serviceOrders, templateServiceTasks, EnumModuleServiceOrders.SeotStatus.INPROGRESS, null);

        List<ServiceOrderTasks> serviceOrderTasks = TransactionMapper.mapListDtoToListEntity(dtoList, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(serviceOrderTasks);
    }

    @Transactional
    @Override
    public int updateTasksStatus(EnumModuleServiceOrders.SeotStatus seotStatus, Long seotId) {
        ServiceOrderTasks orderTasks = soTasksRepository.findById(seotId)
                .orElseThrow(() -> new EntityNotFoundException("::updateTasksStatus ID " + seotId + " is not found"));

        int updateSeot;
        if (orderTasks.getServiceOrderWorkorders().isEmpty() || servOrderWorkorderService.checkWorkorderBySeotId(orderTasks.getSeotId())) {
            updateSeot = soTasksRepository.updateTasksStatus(seotStatus, orderTasks.getSeotId());
        } else {
            throw new TasksNotCompletedException("Workorder tasks are not completed");
        }

        log.info("SoOrderServiceImpl::findSeotById updated in ID {} ", seotId);
        return updateSeot;
    }

    @Transactional
    @Override
    public SeotPartnerDto updateSeotPartner(SeotPartnerDto seotPartnerDto, Long seotId) {
        ServiceOrderTasks orderTasks = soTasksRepository.findById(seotId)
                .orElseThrow(() -> new EntityNotFoundException("::updateSeotPartner() ID " + seotId + " is not found"));
        SeotPartnerDto seotPartner = SeotPartnerDto.builder()
                .partnerId(seotPartnerDto.getPartnerId())
                .repair(seotPartnerDto.getRepair())
                .sparepart(seotPartnerDto.getSparepart())
                .seotStatus(seotPartnerDto.getSeotStatus()).build();

        validateWorkorderForPartner(seotPartnerDto, seotId);

        Partner partner = partnerRepository.findById(seotPartner.getPartnerId())
                .orElseThrow(() -> new EntityNotFoundException("::partnerRepository.findById ID " + seotPartner.getPartnerId() + " is not found"));
        soOrderRepository.selectPartner(partner, orderTasks.getServiceOrders().getSeroId());


        ServiceTaskReqDto serviceTaskReqDto = TransactionMapper.mapEntityToDto(orderTasks, ServiceTaskReqDto.class);
        notifyCurrentTask(serviceTaskReqDto);

        return seotPartner;
    }

    @Override
    public void validateWorkorderForPartner(SeotPartnerDto seotPartnerDto, Long seotId){
        if (seotPartnerDto.getRepair()) {
            serviceWorkorderTransaction.createWorkorderTask("REPAIR", seotId);
            log.info("SoOrderServiceImpl::updateSeotPartner add REPAIR to workorder");
        }

        if (seotPartnerDto.getSparepart()) {
            serviceWorkorderTransaction.createWorkorderTask("GANTI SUKU CADANG", seotId);
            log.info("SoOrderServiceImpl::updateSeotPartner add GANTI SUKU CADANG to workorder");
        }
    }

    @Override
    public List<ServiceTaskReqDto> generateFromTemplateTasks(ServiceOrders serviceOrders, List<TemplateServiceTask> templateServiceTasks,
                                           EnumModuleServiceOrders.SeotStatus seotStatus, Method taskReflection) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();

        for (int i = 0; i < templateServiceTasks.size(); i++) {
            seot.add(new ServiceTaskReqDto(templateServiceTasks.get(i).getTestaName(),
                    serviceOrders.getServices().getServStartdate().plusDays(i),
                    serviceOrders.getServices().getServEnddate().plusDays(i + 1),
                    seotStatus, serviceOrders.getEmployees().getAreaWorkGroup(),
                    serviceOrders, taskReflection));

            //notifyCurrentTask(seot.get(i));

        }
        return seot;
    }

    @Override
    public void notifyCurrentTask(ServiceTaskReqDto serviceOrderTask) {

        ServiceOrderRespDto orderDtoById = servOrderService.getById(serviceOrderTask.getServiceOrders().getSeroId());
        ServiceRespDto services = servService.getById(serviceOrderTask.getServiceOrders().getServices().getServId());

        switch (serviceOrderTask.getSeotName()) {
            case "NOTIFY TO AGENT" ->
                    servOrderTaskService.notifyTask(orderDtoById.getEmployees().getEmployees().getUser().getUserEmail(),
                            "Request new POLIS",
                            "Request new POLIS from " + services.getUserDto().getUserFullName());
            case "NOTIFY TO CUSTOMER" ->
                    servOrderTaskService.notifyTask(services.getUserDto().getUserEmail(),
                            "POLIS has been created",
                            "Your request POLIS has been created, check your dashboard page");
            case "CLAIM DOCUMENT APPROVED" -> servOrderTaskService.notifyTask(services.getServiceOrdersList().get(0).getPartner().getPartnerContacts().get(0).getUser().getUserEmail(),
                    "Repair Sparepart from Customer",
                    "Repair from " + services.getUserDto().getUserFullName());
            case "CALCULATE SPARE PART" -> servOrderTaskService.notifyTask(services.getUserDto().getUserEmail(),
                    "Your car is finish to repair",
                    "Car Repaired is finish");
            case "NOTIFY CUSTOMER VEHICLE REPAIRED" -> servOrderTaskService.notifyTask(services.getUserDto().getUserEmail(),
                    "Claim for " + services.getUserDto().getUserFullName(),

                    "Repaired is finish, pay claim to user");
        }
    }
}
