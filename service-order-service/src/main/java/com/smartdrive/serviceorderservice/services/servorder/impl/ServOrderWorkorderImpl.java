package com.smartdrive.serviceorderservice.services.servorder.impl;

import com.smartdrive.serviceorderservice.dto.request.ServiceWorkorderReqDto;
import com.smartdrive.serviceorderservice.entities.ServiceOrderTasks;
import com.smartdrive.serviceorderservice.entities.ServiceOrderWorkorder;
import com.smartdrive.serviceorderservice.mapper.TransactionMapper;
import com.smartdrive.serviceorderservice.repositories.SoWorkorderRepository;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderWorkorderService;
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
public class ServOrderWorkorderImpl implements ServOrderWorkorderService {

    private final SoWorkorderRepository soWorkorderRepository;
//    private final TewoRepository tewoRepository;

    @Transactional
    @Override
    public List<ServiceOrderWorkorder> addSowoList(List<ServiceOrderTasks> seotList) {

        List<ServiceWorkorderReqDto> sowo = new ArrayList<>();
//        List<TemplateTaskWorkOrder> taskWorkOrders = tewoRepository.findAll();
//        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(0).getTewoName(), LocalDateTime.now(), false, seotList.get(0)));
//        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(1).getTewoName(), LocalDateTime.now(), false, seotList.get(0)));
//        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(2).getTewoName(), LocalDateTime.now(), false, seotList.get(2)));
//        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(3).getTewoName(), LocalDateTime.now(), false, seotList.get(3)));

        List<ServiceOrderWorkorder> workorders = TransactionMapper.mapListDtoToListEntity(sowo, ServiceOrderWorkorder.class);
        return soWorkorderRepository.saveAll(workorders);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrderWorkorder> findSowoBySeotId(Long seotId) {
        return soWorkorderRepository.findSowoBySeotId(seotId);
    }

    @Transactional
    @Override
    public int updateSowoStatus(Boolean sowoStatus, Long sowoId) {
        int updatedSowoStatus = soWorkorderRepository.updateSowoStatus(sowoStatus, LocalDateTime.now(), sowoId);
        log.info("SoOrderServiceImpl::addSoWorkorder updated {} ", updatedSowoStatus);
        return updatedSowoStatus;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkAllWorkComplete(List<ServiceOrderWorkorder> sowoList) {
        boolean checkedAll = false;
        for (ServiceOrderWorkorder item : sowoList) {
            checkedAll = item.getSowoStatus();
        }
        return checkedAll;
    }
}
