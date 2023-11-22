package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.dto.ServicesDto;
import com.app.smartdrive.api.entities.service_order.dto.SoTasksDto;
import com.app.smartdrive.api.entities.service_order.dto.SoWorkorderDto;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceControllerAdapter {

    private SoService soService;
    private SoOrderService soOrderService;
    private SoTasksService soTasksService;

    public ServicesDto generateServiceDto(String seroId){
        Stream<ServiceOrderTasks> seot = soTasksService.findAllBySeotSeroId(seroId);

        List<SoTasksDto> soTasksDtos = seot
                .map(this::generateSoTasksDto)
                .collect(Collectors.toList());

        ServicesDto servicesDto = soOrderService.findDtoById(seroId);
        servicesDto.setServiceOrderTasksList(soTasksDtos);

        return servicesDto;
    }

    public SoTasksDto generateSoTasksDto(ServiceOrderTasks seot){
        List<SoWorkorderDto> workorderDtos = seot.getServiceOrderWorkordersSet().stream()
                .map(this::generateSoWorkorderDto)
                .collect(Collectors.toList());

        return SoTasksDto.builder()
                .taskId(seot.getSeotId())
                .taskName(seot.getSeotName())
                .startDate(seot.getSeotStartDate())
                .endDate(seot.getSeotEndDate())
                .status(seot.getSeotStatus())
                .serviceOrderWorkorder(workorderDtos).build();
    }

    public SoWorkorderDto generateSoWorkorderDto(ServiceOrderWorkorder sowo) {
        return SoWorkorderDto.builder()
                .sowoName(sowo.getSowoName())
                .sowoStatus(sowo.getSowoStatus())
                .build();
    }
}
