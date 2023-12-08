package com.app.smartdrive.api.services.customer.impl;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.repositories.customer.CustomerInscExtendRepository;
import com.app.smartdrive.api.repositories.master.TemiRepository;
import com.app.smartdrive.api.services.customer.CustomerInscExtendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerInscExtendServiceImpl implements CustomerInscExtendService {
    private final TemiRepository temiRepository;

    private final CustomerInscExtendRepository customerInscExtendRepository;

    @Override
    public List<CustomerInscExtend> getCustomerInscEtend(
            Long[] cuexIds,
            CustomerInscAssets cias,
            Long entityId
    ) {
        List<CustomerInscExtend> ciasCuexs = new ArrayList<>();

        for (Long i: cuexIds) {
            Double nominal;

            TemplateInsurancePremi temi = this.temiRepository.findById(i).get();

            if(Objects.nonNull(temi.getTemiRateMin())){
                nominal = temi.getTemiRateMin() * temi.getTemiNominal();
            }else{
                nominal = temi.getTemiNominal();
            }

            CustomerInscExtend cuex = CustomerInscExtend.builder()
                    .cuexName(temi.getTemiName())
                    .cuex_nominal(nominal)
                    .cuexTotalItem(1)
                    .customerInscAssets(cias)
                    .cuexCreqEntityid(entityId)
                    .build();

            ciasCuexs.add(cuex);
        }
        return ciasCuexs;
    }

    @Override
    public void deleteAllCustomerInscExtendInCustomerRequest(Long creqEntityId) {
        this.customerInscExtendRepository.deleteAllByCuexCreqEntityid(creqEntityId);
    }


}
