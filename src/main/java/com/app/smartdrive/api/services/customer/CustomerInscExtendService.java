package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;

import java.util.List;

public interface CustomerInscExtendService {
    public List<CustomerInscExtend> getCustomerInscEtend(
            Long[] cuexIds,
            CustomerInscAssets cias,
            Long entityId
    );

    public void deleteAllCustomerInscExtendInCustomerRequest(Long creqEntityId);
}
