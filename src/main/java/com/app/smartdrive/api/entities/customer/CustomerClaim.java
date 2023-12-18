package com.app.smartdrive.api.entities.customer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_claim", schema = "customer")
@Entity
public class CustomerClaim {
    @Id
    @Column(name = "cucl_creq_entityid")
    private Long  cuclCreqEntityId;

    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "cucl_creq_entityid")
    private CustomerRequest customerRequest;

    @Column(name = "cucl_create_date")
    private LocalDateTime cuclCreateDate;

    @Column(name = "cucl_events")
    private int cuclEvents;

    @Column(name = "cucl_event_price")
    private BigDecimal cuclEventPrice;

    @Column(name = "cucl_subtotal")
    private BigDecimal cuclSubtotal;

    @Column(name = "cucl_reason", length = 256)
    private String cuclReason;

}
