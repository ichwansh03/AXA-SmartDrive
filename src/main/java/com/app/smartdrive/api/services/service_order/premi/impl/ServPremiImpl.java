package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.dto.service_order.response.SemiDto;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
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

    private final ServPremiCreditService servPremiCreditService;

    @Override
    public SemiDto findByServId(Long servId) {
        ServicePremi servicePremi = semiRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("findByServId(Long servId)::servId premi is not found"));

        return TransactionMapper.mapEntityToDto(servicePremi, SemiDto.class);
    }

    @Transactional
    @Override
    public ServicePremi generateServPremi(Services services){
        ServicePremi servicePremi = ServicePremi.builder()
                .semiServId(services.getServId())
                .semiPremiDebet(services.getCustomer().getCustomerInscAssets().getCiasTotalPremi())
                .semiPaidType(services.getCustomer().getCustomerInscAssets().getCiasPaidType().toString())
                .semiStatus(EnumModuleServiceOrders.SemiStatus.UNPAID.toString()).build();

        ServicePremi save = semiRepository.save(servicePremi);
        log.info("service premi {} ", save);

        if (services.getCustomer().getCustomerInscAssets().getCiasPaidType() == EnumCustomer.CreqPaidType.CREDIT) {
            servPremiCreditService.addSecr(save);
            log.info("ServPremiImpl::addSemi successfully added service premi");
        } else {
            servPremiCreditService.addSecrCash(save);
        }

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
