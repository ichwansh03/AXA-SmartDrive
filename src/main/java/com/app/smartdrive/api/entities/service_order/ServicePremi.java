package com.app.smartdrive.api.entities.service_order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_premi", schema = "so")
@NamedQuery(
        name = "ServicePremi.updateSemiStatus",
        query = "UPDATE ServicePremi semi SET semi.semiStatus = :semiStatus WHERE semi.semiServId = :semiServId", lockMode = LockModeType.PESSIMISTIC_WRITE)
@DynamicInsert
@DynamicUpdate
public class ServicePremi {

    @Id
    @Column(name = "semi_serv_id")
    private Long semiServId;

    @Column(name = "semi_premi_debet")
    private BigDecimal semiPremiDebet;

    @Column(name = "semi_premi_credit")
    private BigDecimal semiPremiCredit;

    @Column(name = "semi_paid_type")
    private String semiPaidType;

    @Column(name = "semi_status")
    private String semiStatus;

    @Column(name = "semi_modified_date")
    private LocalDateTime semiModifiedDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "semi_serv_id", referencedColumnName = "serv_id", insertable = false, updatable = false)
    Services services;

}
