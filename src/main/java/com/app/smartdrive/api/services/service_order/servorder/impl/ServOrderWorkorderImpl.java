package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.dto.service_order.request.ServiceWorkorderReqDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
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

    @Transactional
    @Override
    public List<ServiceOrderWorkorder> addSowoList(List<ServiceOrderTasks> seotList) {

        List<ServiceWorkorderReqDto> sowo = new ArrayList<>();
        sowo.add(new ServiceWorkorderReqDto("CHECK UMUR", LocalDateTime.now(), false, seotList.get(0)));
        sowo.add(new ServiceWorkorderReqDto("RELATE GOVERNMENT", LocalDateTime.now(), false, seotList.get(0)));
        sowo.add(new ServiceWorkorderReqDto("PREMI SCHEMA", LocalDateTime.now(), false, seotList.get(2)));
        sowo.add(new ServiceWorkorderReqDto("DOCUMENT DISETUJUI", LocalDateTime.now(), false, seotList.get(3)));

        List<ServiceOrderWorkorder> workorders = TransactionMapper.mapListDtoToListEntity(sowo, ServiceOrderWorkorder.class);

        return soWorkorderRepository.saveAll(workorders);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrderWorkorder> findSowoBySeotId(Long seotId) {
        List<ServiceOrderWorkorder> sowoBySeotId = soWorkorderRepository.findSowoBySeotId(seotId);
        log.info("SoOrderServiceImpl::addSoWorkorder created {} ", sowoBySeotId);
        return sowoBySeotId;
    }

    @Transactional
    @Override
    public int updateSowoStatus(Boolean sowoStatus, Long sowoId) {
        int updatedSowoStatus = soWorkorderRepository.updateSowoStatus(sowoStatus, sowoId);
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
