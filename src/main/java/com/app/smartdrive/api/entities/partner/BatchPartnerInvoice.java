package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.entities.hr.EnumClassHR;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

public class BatchPartnerInvoice {

    private String no;
    private LocalDateTime createdOn;
    private Double subTotal;
    private Double tax;
    private String accountNo;

}
