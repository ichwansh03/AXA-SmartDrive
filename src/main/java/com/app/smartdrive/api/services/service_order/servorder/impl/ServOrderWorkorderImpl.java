package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.BackgroundJob;
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

        List<ServiceOrderWorkorder> sowo = new ArrayList<>();
        sowo.add(new ServiceOrderWorkorder("CHECK UMUR", LocalDateTime.now(), false, seotList.get(0)));
        sowo.add(new ServiceOrderWorkorder("RELATE GOVERNMENT", LocalDateTime.now(), false, seotList.get(0)));
        sowo.add(new ServiceOrderWorkorder("PREMI SCHEMA", LocalDateTime.now(), false, seotList.get(2)));
        sowo.add(new ServiceOrderWorkorder("DOCUMENT DISETUJUI", LocalDateTime.now(), false, seotList.get(2)));



        return soWorkorderRepository.saveAll(sowo);
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
    public List<ServiceOrderTasks> checkAllWorkComplete(List<ServiceOrderWorkorder> sowoList, ServiceOrderWorkorder sowo) {
        return null;
    }
}
