package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerInscAssetsService {
    public CustomerInscAssets createCustomerInscAssets(
            Long entityId,
            CiasDTO ciasDTO,
            CarSeries carSeries,
            Cities existCity,
            InsuranceType existInty,
            CustomerRequest newCustomerRequest
    );

    public void updateCustomerInscAssets(
            CustomerInscAssets cias,
            CiasDTO ciasUpdateDTO,
            Cities existCity,
            CarSeries carSeries,
            InsuranceType existInty
    );

    public Double getPremiPrice(
            String insuraceType,
            String carBrand,
            Long zonesId,
            Double currentPrice,
            List<CustomerInscExtend> cuexs
    );
}
