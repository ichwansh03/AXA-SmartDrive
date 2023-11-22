package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.dto.service_order.ServicesDto;
import com.app.smartdrive.api.dto.service_order.SoTasksDto;
import com.app.smartdrive.api.dto.service_order.SoWorkorderDto;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public String formatServiceOrderId(EnumCustomer.CreqType servType, Long seroId, LocalDate createdAt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatSeroId = String.format("%04d", seroId);

        if (servType.equals("POLIS")) return "PL"+formatSeroId+"-"+createdAt.format(formatter);
        else if (servType.equals("CLAIM")) return "CL"+formatSeroId+"-"+createdAt.format(formatter);

        return "TP"+formatSeroId+"-"+createdAt.format(formatter);
    }

}
