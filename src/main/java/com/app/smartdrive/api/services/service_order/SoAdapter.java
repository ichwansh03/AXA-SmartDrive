package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;


@NoArgsConstructor
@Component
@Slf4j
public class SoAdapter {

    private int seroSequence = 0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String formatServiceOrderId(Services services){

        String servTypes = services.getServType().toString();

        log.info("Format ID for ServiceOrders has been created");

        String formatSeroId = String.format("%04d", getNextSequenceNumber());
        String formatEndDate = services.getServCreatedOn().format(formatter);

        String formatId;

        switch (servTypes) {
            case "POLIS" -> formatId = "PL" + formatSeroId + "-" + formatEndDate;
            case "CLAIM" -> formatId = "CL" + formatSeroId + "-" + formatEndDate;
            case "FEASIBLITY" -> formatId = "FS" + formatSeroId + "-" + formatEndDate;
            default -> formatId = "TP" + formatSeroId + "-" + formatEndDate;
        }

        return formatId;
    }

    private synchronized int getNextSequenceNumber() {
        // Increment sequence number and return the updated value
        return ++seroSequence;
    }

    public String generatePolis(CustomerRequest cr){
        String servTypes = cr.getCreqType().toString();
        String createdDate = cr.getCreqCreateDate().format(formatter);
        String formatPolisId = String.format("%04d", getNextSequenceNumber());

        return switch (servTypes) {
            case "POLIS", "CLAIM" -> formatPolisId+"-"+createdDate;
            default -> "-";
        };

    }


}
