package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.dto.EmailReq;
import com.app.smartdrive.api.dto.service_order.request.SeotPartnerDto;
import com.app.smartdrive.api.dto.service_order.request.ServiceTaskReqDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.master.EmailService;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskImpl implements ServOrderTaskService {

    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;

    private final TestaRepository testaRepository;
    private final TewoRepository tewoRepository;
    private final PartnerRepository partnerRepository;

    private final EmailService emailService;

    @Transactional
    @Override
    public List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();

        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(1L);

        for (int i = 0; i < templateServiceTasks.size(); i++) {
            seot.add(new ServiceTaskReqDto(templateServiceTasks.get(i).getTestaName(),
                    serviceOrders.getServices().getServStartDate().plusDays(i),
                    serviceOrders.getServices().getServStartDate().plusDays(i+1),
                    EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getEmployees().getAreaWorkGroup(),
                    serviceOrders, null));
        }

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);
        List<ServiceOrderTasks> serviceOrderTasks = soTasksRepository.saveAll(mapperTaskList);

        ServOrderWorkorderImpl servOrderWorkorder = new ServOrderWorkorderImpl(soWorkorderRepository, tewoRepository);
        servOrderWorkorder.addSowoList(serviceOrderTasks);

        return serviceOrderTasks;
    }

    @Override
    public List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders) throws Exception {

        List<ServiceTaskReqDto> seotList = new ArrayList<>();
        EmailReq emailReq = new EmailReq();
        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(2L);

        Method generatePolisNumber = SoAdapter.class.getMethod("generatePolis", CustomerRequest.class);

        for (TemplateServiceTask templateServiceTask : templateServiceTasks) {
            seotList.add(new ServiceTaskReqDto(templateServiceTask.getTestaName(),
                    serviceOrders.getServices().getServStartDate(),
                    serviceOrders.getServices().getServStartDate().plusDays(1),
                    EnumModuleServiceOrders.SeotStatus.COMPLETED, serviceOrders.getEmployees().getAreaWorkGroup(),
                    serviceOrders, generatePolisNumber));

            switch (templateServiceTask.getTestaName()){
                case "NOTIFY TO AGENT" -> notifyTask(emailReq, serviceOrders,
                        "Request new POLIS",
                        "Request new POLIS from "+serviceOrders.getServices().getUsers().getUserFullName());
                case "NOTIFY TO CUSTOMER" -> notifyTask(emailReq, serviceOrders,
                        "POLIS has been created",
                        "Your request POLIS has been created, check your dashboard page");

            }
        }

        log.info("ServOrderTaskImpl::addPolisList the result of number polis is {} ", generatePolisNumber);

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seotList, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(mapperTaskList);
    }

    @Override
    public List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();
        EmailReq emailReq = new EmailReq();
        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(3L);

        for (int i = 0; i < templateServiceTasks.size(); i++) {
            seot.add(new ServiceTaskReqDto(templateServiceTasks.get(i).getTestaName(),
                    serviceOrders.getServices().getServStartDate().plusDays(i),
                    serviceOrders.getServices().getServStartDate().plusDays(i+1),
                    EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getEmployees().getAreaWorkGroup(),
                    serviceOrders, null));

            switch (templateServiceTasks.get(i).getTestaName()){
                case "NOTIFY PARTNER TO REPAIR" -> {
                    if (seot.get(i).getSeotStatus() == EnumModuleServiceOrders.SeotStatus.COMPLETED) {
                        notifyTask(emailReq, serviceOrders,
                                "Repair Sparepart from Customer",
                                "New claim request from "+serviceOrders.getServices().getUsers().getUserFullName());
                    }
                }
                case "NOTIFY AGENT CLAIM" -> {
                    if (seot.get(i).getSeotStatus() == EnumModuleServiceOrders.SeotStatus.COMPLETED) {
                        notifyTask(emailReq, serviceOrders,
                                "New CLAIM Request",
                                "New claim request from "+serviceOrders.getServices().getUsers().getUserFullName());
                    }
                }
            }
        }

        List<ServiceOrderTasks> serviceOrderTasks = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(serviceOrderTasks);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrderTasks> findSeotBySeroId(String seroId) {
        List<ServiceOrderTasks> orderTasks = soTasksRepository.findByServiceOrders_SeroId(seroId);

        if (orderTasks.isEmpty()) {
            throw new EntityNotFoundException("ID "+seroId+" is not found");
        }

        log.info("SoOrderServiceImpl::findSeotBySeroId in ID {} ",seroId);

        return orderTasks;
    }

    @Transactional
    @Override
    public int updateTasksStatus(EnumModuleServiceOrders.SeotStatus seotStatus, Long seotId) {
        int updateSeot = soTasksRepository.updateTasksStatus(seotStatus, seotId);

        if (updateSeot == 0) {
            throw new ValidasiRequestException("Failed to update data",400);
        }
        log.info("SoOrderServiceImpl::findSeotById updated in ID {} ",seotId);
        return updateSeot;
    }

    @Transactional
    @Override
    public SeotPartnerDto updateSeotPartner(SeotPartnerDto seotPartnerDto, Long seotId) {
        ServiceOrderTasks orderTasks = soTasksRepository.findById(seotId).get();
        SeotPartnerDto seotPartner = SeotPartnerDto.builder()
                .partnerId(seotPartnerDto.getPartnerId())
                .repair(seotPartnerDto.getRepair())
                .sparepart(seotPartnerDto.getSparepart())
                .seotStatus(seotPartnerDto.getSeotStatus()).build();

        if (seotPartnerDto.getRepair()) {
            createWorkorderTask("REPAIR", seotId);
            log.info("SoOrderServiceImpl::updateSeotPartner add REPAIR to workorder");
        }

        if (seotPartnerDto.getSparepart()) {
            createWorkorderTask("GANTI SUKU CADANG", seotId);
            log.info("SoOrderServiceImpl::updateSeotPartner add GANTI SUKU CADANG to workorder");
        }

        Partner partner = partnerRepository.findById(seotPartner.getPartnerId()).get();
        soOrderRepository.selectPartner(partner, orderTasks.getServiceOrders().getSeroId());

        return seotPartner;
    }

    private void notifyTask(EmailReq emailReq, ServiceOrders serviceOrders, String subject, String message){
        emailReq.setTo(serviceOrders.getEmployees().getEmployees().getUser().getUserEmail());
        emailReq.setSubject(subject);
        emailReq.setBody(message);
        log.info("Email has been send");
        emailService.sendMail(emailReq);
    }

    @Transactional
    private void createWorkorderTask(String sowoName, Long seotId){
        TemplateTaskWorkOrder workOrder = new TemplateTaskWorkOrder();
        workOrder.setTewoTestaId(seotId);
        workOrder.setTewoName(sowoName);
        tewoRepository.save(workOrder);

        ServiceOrderTasks tasks = soTasksRepository.findById(seotId).get();

        ServiceOrderWorkorder soWorkorder = new ServiceOrderWorkorder();
        soWorkorder.setSowoName(workOrder.getTewoName());
        soWorkorder.setServiceOrderTasks(tasks);
        soWorkorder.setSowoStatus(false);
        soWorkorder.setSowoModDate(LocalDateTime.now());

        soWorkorderRepository.save(soWorkorder);
    }

}
