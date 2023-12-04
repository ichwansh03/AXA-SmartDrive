package com.app.smartdrive.api.entities.customer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long  cuclCreqEntityid;

    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "cucl_creq_entityid")
    private CustomerRequest customerRequest;

    @Column(name = "cucl_create_date")
    private LocalDateTime cuclCreateDate;

    @Column(name = "cucl_event_price")
    private Double cuclEventPrice;

    @Column(name = "cucl_subtotal")
    private Double cuclSubtotal;

    @Column(name = "cucl_reason", length = 256)
    private String cuclReason;

}
