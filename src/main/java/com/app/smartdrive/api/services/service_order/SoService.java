package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.BaseService;
import com.app.smartdrive.api.services.HR.EmployeesService;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

public interface SoService {

    Services addServices(CustomerRequest customerRequest,
                         CustomerInscAssets customerInscAssets,
                         User user,
                         Long creqId);
}
