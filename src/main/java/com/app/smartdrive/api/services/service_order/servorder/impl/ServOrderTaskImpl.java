package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.dto.EmailReq;
import com.app.smartdrive.api.dto.service_order.request.ServiceTaskReqDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
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

            switch (templateServiceTask.getTestaName()){
                case "NOTIFY TO AGENT" -> notifyTask(emailReq, serviceOrders,
                        "Request new POLIS",
                        "Request new POLIS from "+serviceOrders.getServices().getUsers().getUserFullName());
                case "NOTIFY TO CUSTOMER" -> notifyTask(emailReq, serviceOrders,
                        "POLIS has been created",
                        "Your request POLIS has been created, check your dashboard page");
                default -> log.info("ServOrderTaskImpl::addPolisList all notify in POLIS has been created");

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
                //pilih partner berdasarkan area workgroup
                //wo1-pilih partner
                //wo2-call method add partner(so, partner)
                //wo3-notify to repair or ganti suku cadang or keduanya
                case "NOTIFY PARTNER TO REPAIR" -> {
                    if (seot.get(i).getSeotStatus() == EnumModuleServiceOrders.SeotStatus.COMPLETED) {
                        notifyTask(emailReq, serviceOrders,
                                "Check Vehicle Condition",
                                "Check condition of customer vehicle from "+serviceOrders.getServices().getUsers().getUserFullName());
                    }
                }
                //task calculate sparepart call createOne(String seroId) => BPIN Service
                case "NOTIFY AGENT CLAIM" -> {
                    if (seot.get(i).getSeotStatus() == EnumModuleServiceOrders.SeotStatus.COMPLETED) {
                        notifyTask(emailReq, serviceOrders,
                                "New CLAIM Request",
                                "New claim request from "+serviceOrders.getServices().getUsers().getUserFullName());
                    }
                }
                default -> log.info("ServOrderTaskImpl::addClaimList all notify in CLAIM has been created");
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
        int updateSeot = soTasksRepository.updateTasksStatus(seotStatus, seotId);
        ServiceOrderTasks orderTasks = soTasksRepository.findById(seotId).get();

        switch (orderTasks.getSeotName()){
            case "CLAIM DOCUMENT APPROVED" -> {
                //call partner
            }
        }
        log.info("SoOrderServiceImpl::findSeotById updated in ID {} ",seotId);
        return updateSeot;
    }

    private void notifyTask(EmailReq emailReq, ServiceOrders serviceOrders, String subject, String message){
        emailReq.setTo(serviceOrders.getEmployees().getEmployees().getUser().getUserEmail());
        emailReq.setSubject(subject);
        emailReq.setBody(message);
        log.info("Email has been send");
        emailService.sendMail(emailReq);
    }
}
