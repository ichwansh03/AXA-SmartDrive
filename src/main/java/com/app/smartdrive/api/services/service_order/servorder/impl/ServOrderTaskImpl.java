package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.dto.EmailReq;
import com.app.smartdrive.api.dto.service_order.request.ServiceTaskReqDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskImpl implements ServOrderTaskService {

    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;
    private final TestaRepository testaRepository;
    private final TewoRepository tewoRepository;
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

        log.info("ServOrderTaskImpl::addFeasiblityList successfully saved {} ",serviceOrderTasks);
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

            if (templateServiceTask.getTestaName().equals("NOTIFY TO AGENT")) {
                emailReq.setTo(serviceOrders.getEmployees().getEmployees().getUser().getUserEmail());
                emailReq.setSubject("New Customer POLIS");
                emailReq.setBody("New Customer POLIS with name " + serviceOrders.getServices().getUsers().getUserFullName());
                log.info("Email send to agent");
                emailService.sendMail(emailReq);
            }

            if (templateServiceTask.getTestaName().equals("NOTIFY TO CUSTOMER")) {
                emailReq.setTo(serviceOrders.getEmployees().getEmployees().getUser().getUserEmail());
                emailReq.setSubject("New POLIS has been created");
                emailReq.setBody("Your successfully created POLIS");
                log.info("Email send to customer");
                emailService.sendMail(emailReq);
            }
        }

        log.info("ServOrderTaskImpl::addPolisList the result of number polis is {} ", generatePolisNumber);

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seotList, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(mapperTaskList);
    }

    @Override
    public List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();
        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(3L);

        for (int i = 0; i < templateServiceTasks.size(); i++) {
            seot.add(new ServiceTaskReqDto(templateServiceTasks.get(i).getTestaName(),
                    serviceOrders.getServices().getServStartDate().plusDays(i),
                    serviceOrders.getServices().getServStartDate().plusDays(i+1),
                    EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getEmployees().getAreaWorkGroup(),
                    serviceOrders, null));

            if (templateServiceTasks.get(i).getTestaName().equals("NOTIFY PARTNER TO REPAIR")){

            }
        }

        List<ServiceOrderTasks> serviceOrderTasks = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(serviceOrderTasks);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrderTasks> findSeotBySeroId(String seroId) {
        List<ServiceOrderTasks> seotBySeroId = soTasksRepository.findByServiceOrders_SeroId(seroId);
        log.info("SoOrderServiceImpl::findSeotById in ID start from {} ", seotBySeroId.get(0));
        return seotBySeroId;
    }

    @Transactional
    @Override
    public int updateTasksStatus(EnumModuleServiceOrders.SeotStatus seotStatus, Long seotId) {
        int serviceOrderTasks = soTasksRepository.updateTasksStatus(seotStatus, seotId);
        log.info("SoOrderServiceImpl::findSeotById updated in ID {} ",seotId);
        return serviceOrderTasks;
    }

}
