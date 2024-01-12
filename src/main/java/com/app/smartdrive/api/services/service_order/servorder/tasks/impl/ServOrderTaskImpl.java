package com.app.smartdrive.api.services.service_order.servorder.tasks.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.EmailReq;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.services.master.EmailService;
import com.app.smartdrive.api.services.service_order.servorder.tasks.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.workorders.ServOrderWorkorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskImpl implements ServOrderTaskService {

    private final SoTasksRepository soTasksRepository;
    private final EmailService emailService;
    private final ServOrderWorkorderService servOrderWorkorderService;


    @Override
    public SoTasksDto findTaskById(Long seotId) {
        ServiceOrderTasks orderTasks = soTasksRepository.findById(seotId)
                .orElseThrow(() -> new EntityNotFoundException("ServOrderTaskImpl::findTaskById ID is not found"));
        return TransactionMapper.mapEntityToDto(orderTasks, SoTasksDto.class);
    }

    @Override
    public List<SoTasksDto> findAllTaskByOrderId(String seroId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<ServiceOrderTasks> orderTasks = soTasksRepository.findByServiceOrders_SeroId(seroId);
        return orderTasks.stream()
                .map(seot -> {
                    SoTasksDto soTasksDto = TransactionMapper.mapEntityToDto(seot, SoTasksDto.class);
                    List<ServiceOrderWorkorder> sowoBySeotId = servOrderWorkorderService.findSowoBySeotId(soTasksDto.getSeotId());
                    soTasksDto.setSeotActualStartdate(seot.getSeotActualStartdate().format(formatter));
                    soTasksDto.setSeotActualEnddate(seot.getSeotActualEnddate().format(formatter));
                    soTasksDto.setServiceOrderWorkorders(TransactionMapper.mapEntityListToDtoList(sowoBySeotId, SoWorkorderDto.class));
                    return soTasksDto;
                })
                .collect(toList());
    }

    @Override
    public void notifyTask(String mailTo, String subject, String message){
        EmailReq emailReq = new EmailReq();
        emailReq.setTo(mailTo);
        emailReq.setSubject(subject);
        emailReq.setBody(message);
        log.info("Email has been send");
        emailService.sendMail(emailReq);
    }

    @Override
    public boolean checkAllTaskComplete(String seroId) {

        List<ServiceOrderTasks> seotBySeroId = soTasksRepository.findByServiceOrders_SeroId(seroId);

        List<ServiceOrderWorkorder> sowoBySeotId = servOrderWorkorderService.findSowoBySeotId(seotBySeroId.get(0).getSeotId());
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
}
