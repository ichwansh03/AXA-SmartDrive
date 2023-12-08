package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;

public interface CustomerInscAssetsService {
    public CustomerInscAssets createCustomerInscAssets(
            Long entityId,
            CiasDTO ciasDTO,
            CarSeries carSeries,
            Cities existCity,
            InsuranceType existInty,
            CustomerRequest newCustomerRequest
    );

    public void updateCustomerInscAssets(CustomerInscAssets cias,
                                         CiasDTO ciasUpdateDTO,
                                         Cities existCity,
                                         CarSeries carSeries,
                                         InsuranceType existInty);


}
