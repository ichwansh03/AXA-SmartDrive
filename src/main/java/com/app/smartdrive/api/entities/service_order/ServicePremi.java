package com.app.smartdrive.api.entities.service_order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_premi", schema = "so")
public class ServicePremi {

    @Id
    @Column(name = "semi_serv_id", unique = true)
    private Long semiServId;

    @Column(name = "semi_premi_debet")
    private Double semiPremiDebet;

    @Column(name = "semi_premi_credit")
    private Double semiPremiCredit;

    @Column(name = "semi_paid_type")
    @Size(max = 15)
    private String semiPaidType;

    //should be enum
    @Column(name = "semi_status")
    @Size(max = 15)
    private String semiStatus;

    @Column(name = "semi_modified_date")
    private LocalDate semiModifiedDate;

    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "semi_serv_id", referencedColumnName = "serv_id")
    Services services;
}
