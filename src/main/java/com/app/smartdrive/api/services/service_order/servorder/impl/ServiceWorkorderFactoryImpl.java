package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.dto.service_order.request.ServiceWorkorderReqDto;
import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServiceWorkorderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceWorkorderFactoryImpl implements ServiceWorkorderFactory {

    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;
    private final TewoRepository tewoRepository;

    @Transactional
    @Override
    public List<ServiceOrderWorkorder> addSowoList(List<ServiceOrderTasks> seotList) {

        List<ServiceWorkorderReqDto> sowo = new ArrayList<>();
        List<TemplateTaskWorkOrder> taskWorkOrders = tewoRepository.findAll();
        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(0).getTewoName(), LocalDateTime.now(), false, seotList.get(0)));
        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(1).getTewoName(), LocalDateTime.now(), false, seotList.get(0)));
        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(2).getTewoName(), LocalDateTime.now(), false, seotList.get(2)));
        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(3).getTewoName(), LocalDateTime.now(), false, seotList.get(3)));

        List<ServiceOrderWorkorder> workorders = TransactionMapper.mapListDtoToListEntity(sowo, ServiceOrderWorkorder.class);
        return soWorkorderRepository.saveAll(workorders);
    }

    @Transactional
    @Override
    public int updateSowoStatus(Boolean sowoStatus, Long sowoId) {
        int updatedSowoStatus = soWorkorderRepository.updateSowoStatus(sowoStatus, LocalDateTime.now(), sowoId);

        if (updatedSowoStatus == 0) {
            throw new ValidasiRequestException("Failed to update data ",400);
        }

        log.info("SoOrderServiceImpl::addSoWorkorder updated in ID {} ", sowoId);
        return updatedSowoStatus;
    }

    @Transactional
    @Override
    public void createWorkorderTask(String sowoName, Long seotId){
        TemplateTaskWorkOrder templateTask = createTemplateTask(sowoName, seotId);
        ServiceOrderTasks tasks = soTasksRepository.findById(seotId)
                .orElseThrow(() -> new EntityNotFoundException("::createWorkorderTask ID is not found"));

        ServiceOrderWorkorder soWorkorder = new ServiceOrderWorkorder();
        soWorkorder.setSowoName(templateTask.getTewoName());
        soWorkorder.setServiceOrderTasks(tasks);
        soWorkorder.setSowoStatus(false);
        soWorkorder.setSowoModDate(LocalDateTime.now());

        soWorkorderRepository.save(soWorkorder);
    }

    private TemplateTaskWorkOrder createTemplateTask(String taskName, Long taskId) {
        TemplateTaskWorkOrder workOrder = new TemplateTaskWorkOrder();
        workOrder.setTewoTestaId(taskId);
        workOrder.setTewoName(taskName);
        return tewoRepository.save(workOrder);
    }
}