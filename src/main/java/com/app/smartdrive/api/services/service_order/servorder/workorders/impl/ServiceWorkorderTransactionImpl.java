package com.app.smartdrive.api.services.service_order.servorder.workorders.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.dto.service_order.request.ServiceWorkorderReqDto;
import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.workorders.ServiceWorkorderTransaction;
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
public class ServiceWorkorderTransactionImpl implements ServiceWorkorderTransaction {

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

        ServiceOrderWorkorder workorder = soWorkorderRepository.findById(sowoId).get();
        if (updateTaskByWorkorders(workorder)){
            soTasksRepository.updateTasksStatus(EnumModuleServiceOrders.SeotStatus.COMPLETED, workorder.getServiceOrderTasks().getSeotId());
        }

        log.info("SoOrderServiceImpl::addSoWorkorder updated in ID {} ", sowoId);
        return updatedSowoStatus;
    }

    @Override
    public boolean updateTaskByWorkorders(ServiceOrderWorkorder workorder){
        List<ServiceOrderWorkorder> orderWorkorders = soWorkorderRepository.findByServiceOrderTasks_SeotId(workorder.getServiceOrderTasks().getSeotId());

        boolean completed = true;
        for (ServiceOrderWorkorder orderWorkorder : orderWorkorders) {
            if (!orderWorkorder.getSowoStatus()) {
                completed = false;
            }
        }

        return completed;
    }

    @Transactional
    @Override
    public void createWorkorderTask(String sowoName, Long seotId){
        ServiceOrderTasks tasks = soTasksRepository.findById(seotId)
                .orElseThrow(() -> new EntityNotFoundException("::createWorkorderTask ID is not found"));
        ServiceOrderWorkorder soWorkorder = new ServiceOrderWorkorder();
        soWorkorder.setSowoName(sowoName);
        soWorkorder.setServiceOrderTasks(tasks);
        soWorkorder.setSowoStatus(false);
        soWorkorder.setSowoModifiedDate(LocalDateTime.now());

        soWorkorderRepository.save(soWorkorder);
    }

}
