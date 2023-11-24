package com.app.smartdrive.api.entities.customer;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
