package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServPremiImpl implements ServPremiService {

    private final SemiRepository semiRepository;
    private final SoRepository soRepository;

    private final ServPremiCreditService servPremiCreditService;

    @Transactional(readOnly = true)
    @Override
    public List<ServicePremi> findByServId(Long servId) {
        List<ServicePremi> allBySemiServId = semiRepository.findByServices_ServId(servId);
        log.info("ServPremiImpl::findAllBySemiServId successfully viewed");
        return allBySemiServId;
    }

    @Transactional
    @Override
    public ServicePremi addSemi(ServicePremi servicePremi, Long servId) {
        Services services = soRepository.findById(servId).get();

        ServicePremi premi = ServicePremi.builder()
                .semiServId(services.getServId())
                .semiPremiDebet(servicePremi.getSemiPremiDebet())
                .semiPremiCredit(servicePremi.getSemiPremiCredit())
                .semiStatus(servicePremi.getSemiStatus())
                .semiPaidType(servicePremi.getSemiPaidType())
                .semiModifiedDate(LocalDateTime.now())
                .build();

        ServicePremi save = semiRepository.save(premi);

        servPremiCreditService.addSecr(premi);
        log.info("ServPremiImpl::addSemi successfully added service premi");
        return save;
    }

    @Override
    public int updateSemiStatus(String semiStatus, Long semiServId) {
        int updated = semiRepository.updateSemiStatus(semiStatus, semiServId);
        log.info("ServPremiImpl::updateSemiStatus successfully updated {} ", updated);
        return updated;
    }

//    @Scheduled(cron = "* * * * * *")
//    public void dueDateSemi(){
//        //check jika
//        log.info("ServPremiImpl::update service premi");
//    }

}
