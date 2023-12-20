package com.smartdrive.serviceorderservice.services.premi.impl;

import com.smartdrive.serviceorderservice.Exceptions.ValidasiRequestException;
import com.smartdrive.serviceorderservice.entities.ServicePremi;
import com.smartdrive.serviceorderservice.entities.Services;
import com.smartdrive.serviceorderservice.repositories.SemiRepository;
import com.smartdrive.serviceorderservice.repositories.SoRepository;
import com.smartdrive.serviceorderservice.services.premi.ServPremiCreditService;
import com.smartdrive.serviceorderservice.services.premi.ServPremiService;
import jakarta.persistence.EntityNotFoundException;
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

        servPremiCreditService.addSecr(premi);
        log.info("ServPremiImpl::addSemi successfully added service premi");
        return save;
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
