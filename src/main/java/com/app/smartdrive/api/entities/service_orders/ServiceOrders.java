package com.app.smartdrive.api.entities.service_orders;

import com.app.smartdrive.api.entities.service_orders.enumerated.EnumModuleServiceOrders;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_orders", schema = "so")
public class ServiceOrders {

    @Id
    @Column(name = "sero_id")
    @Size(max = 25)
    private String seroId;

    @Column(name = "sero_ordt_type")
    @Size(max = 15)
    private String seroOrdtType;

    @Column(name = "sero_status")
    @Size(max = 15)
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroStatus seroStatus;

    @Column(name = "sero_reason")
    private String seroReason;

    @Column(name = "serv_claim_no")
    @Size(max = 12)
    private String servClaimNo;

    @Column(name = "serv_claim_startdate")
    private LocalDate servClaimStartdate;

    @Column(name = "serv_claim_enddate")
    private LocalDate servClaimEnddate;
}