package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.dto.customer.request.CustomerInscAssetsRequestDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerInscAssetsService {
    public CustomerInscAssets createCustomerInscAssets(
            Long entityId,
            CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO,
            CarSeries carSeries,
            Cities existCity,
            InsuranceType existInty,
            CustomerRequest newCustomerRequest
    );

    public void updateCustomerInscAssets(
            CustomerInscAssets cias,
            CustomerInscAssetsRequestDTO ciasUpdateDTO,
            Cities existCity,
            CarSeries carSeries,
            InsuranceType existInty
    );

    public BigDecimal getPremiPrice(
            String insuraceType,
            String carBrand,
            Long zonesId,
            BigDecimal currentPrice,
            int age,
            List<CustomerInscExtend> cuexs
    );

    public void validatePoliceNumber(String policeNumber);
}
