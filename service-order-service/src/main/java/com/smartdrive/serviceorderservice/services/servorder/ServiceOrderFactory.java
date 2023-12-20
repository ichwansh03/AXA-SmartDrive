package com.smartdrive.serviceorderservice.services.servorder;


import com.smartdrive.serviceorderservice.entities.ServiceOrders;
import com.smartdrive.serviceorderservice.entities.Services;

import java.time.LocalDateTime;

public interface ServiceOrderFactory {

    ServiceOrders generateSeroFeasiblity(Services services);

    ServiceOrders handlePolisAndClaim(Services services, LocalDateTime startDate, LocalDateTime endDate, String prefixSeroId);

}
