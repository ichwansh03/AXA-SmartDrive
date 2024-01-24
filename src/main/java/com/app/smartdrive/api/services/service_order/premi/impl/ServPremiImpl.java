package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServPremiImpl implements ServPremiService {

    private final SemiRepository semiRepository;
    private final SoRepository soRepository;

    private final ServPremiCreditService servPremiCreditService;

    @Override
    public ServicePremi findByServId(Long servId) {
        return semiRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("findByServId(Long servId)::servId premi is not found"));
    }

    @Transactional
    @Override
    public ServicePremi addSemi(ServicePremi servicePremi, Long servId) {
        Services services = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("addSemi(ServicePremi servicePremi, Long servId)::servId is not found"));

        ServicePremi premi = ServicePremi.builder()
                .semiServId(services.getServId())
                .semiPremiDebet(servicePremi.getSemiPremiDebet())
                .semiPremiCredit(servicePremi.getSemiPremiCredit())
                .semiStatus(servicePremi.getSemiStatus())
                .semiPaidType(servicePremi.getSemiPaidType())
                .semiModifiedDate(LocalDateTime.now())
                .build();

        ServicePremi save = semiRepository.save(premi);

        if (services.getCustomer().getCustomerInscAssets().getCiasPaidType() == EnumCustomer.CreqPaidType.CREDIT) {
            servPremiCreditService.addSecr(premi);
            log.info("ServPremiImpl::addSemi successfully added service premi");
        } else {
            servPremiCreditService.addSecrCash(premi);
        }

        return save;
    }

    @Override
    public ServicePremi generateServPremi(Services services){
        ServicePremi servicePremi = ServicePremi.builder()
                .semiServId(services.getServId())
                .semiPremiDebet(services.getCustomer().getCustomerInscAssets().getCiasTotalPremi())
                .semiPaidType(services.getCustomer().getCustomerInscAssets().getCiasPaidType().toString())
                .semiStatus(EnumModuleServiceOrders.SemiStatus.UNPAID.toString()).build();
        log.info("service premi {} ", services);
        addSemi(servicePremi, services.getServId());
        return servicePremi;
    }

    @Transactional
    @Override
    public int updateSemiStatus(String semiStatus, Long semiServId) {
        int updated = semiRepository.updateSemiStatus(semiStatus, semiServId);

        if (updated == 0) {
            throw new ValidasiRequestException("Failed to update data",400);
        }

        log.info("ServPremiImpl::updateSemiStatus successfully updated {} ", updated);
        return updated;
    }

}
