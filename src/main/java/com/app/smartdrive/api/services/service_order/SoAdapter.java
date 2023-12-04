package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@Slf4j
public class SoAdapter {

    private ServService servService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String formatServiceOrderId(Services services){

        String servTypes = services.getServType().toString();

        log.info("Format ID for ServiceOrders has been created");

        String formatSeroId = String.format("%04d", services.getUsers().getUserEntityId());
        String formatEndDate = services.getServStartDate().format(formatter);

        return switch (servTypes) {
            case "POLIS" -> "PL" + formatSeroId + "-" + formatEndDate;
            case "CLAIM" -> "CL" + formatSeroId + "-" + formatEndDate;
            case "FEASIBLITY" -> "FS" + formatSeroId + "-" + formatEndDate;
            default -> "TP" + formatSeroId + "-" + formatEndDate;
        };

    }

    public String generatePolisNumber(CustomerRequest customerRequest){
        String servTypes = customerRequest.getCreqType().toString();
        String createdDate = customerRequest.getCreqCreateDate().format(formatter);
        String formatPolisId = String.format("%03d", customerRequest.getCustomer().getUserEntityId());

        return switch (servTypes) {
            case "POLIS", "CLAIM" -> formatPolisId+"-"+createdDate;
            default -> "-";
        };

    }

}
