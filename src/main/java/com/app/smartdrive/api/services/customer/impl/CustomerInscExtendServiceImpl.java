package com.app.smartdrive.api.services.customer.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.repositories.customer.CustomerInscExtendRepository;
import com.app.smartdrive.api.repositories.master.TemiRepository;
import com.app.smartdrive.api.services.customer.CustomerInscExtendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerInscExtendServiceImpl implements CustomerInscExtendService {
    private final TemiRepository temiRepository;

    private final CustomerInscExtendRepository customerInscExtendRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CustomerInscExtend> getCustomerInscEtend(
            Long[] cuexIds,
            CustomerInscAssets cias,
            Long entityId,
            BigDecimal currentPrice
    ) {
        List<CustomerInscExtend> ciasCuexs = new ArrayList<>();

        for (Long i: cuexIds) {
            BigDecimal nominal;

            TemplateInsurancePremi temi = this.temiRepository.findById(i).orElseThrow(
                    () -> new EntityNotFoundException("Template Insurance Premi with id " + i + " is not found")
            );

            if(Objects.nonNull(temi.getTemiRateMin())){
                Double rate = temi.getTemiRateMin() / 100;
                BigDecimal rateBig = BigDecimal.valueOf(rate);
                nominal = currentPrice.multiply(rateBig);
            }else{
//                nominal = temi.getTemiNominal();
            }

            CustomerInscExtend cuex = CustomerInscExtend.builder()
                    .cuexName(temi.getTemiName())
//                    .cuexNominal(nominal)
                    .cuexTotalItem(1)
                    .customerInscAssets(cias)
                    .cuexCreqEntityid(entityId)
                    .build();

            ciasCuexs.add(cuex);
        }

        log.info("CustomerInscExtendServiceImpl::getCustomerInscEtend, generate cuex from temi");
        return ciasCuexs;
    }

    @Transactional
    @Override
    public void deleteAllCustomerInscExtendInCustomerRequest(Long creqEntityId) {
        this.customerInscExtendRepository.deleteAllByCuexCreqEntityid(creqEntityId);
        log.info("CustomerInscExtendServiceImpl::deleteAllCustomerInscExtendInCustomerRequest, delete all cuex for inserting a new one");
    }


}
