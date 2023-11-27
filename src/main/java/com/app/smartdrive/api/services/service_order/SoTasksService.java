package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;

import java.util.stream.Stream;

public interface SoTasksService {

    Stream<ServiceOrderTasks> findAllBySeotSeroId(Long seroId);
}
