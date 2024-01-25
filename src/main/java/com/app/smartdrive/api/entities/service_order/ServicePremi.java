package com.app.smartdrive.api.entities.service_order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "so")
@NamedQuery(
        name = "ServicePremi.updateSemiStatus",
        query = "UPDATE ServicePremi semi SET semi.semiStatus = :semiStatus WHERE semi.semiServId = :semiServId", lockMode = LockModeType.PESSIMISTIC_WRITE)
@DynamicInsert
@DynamicUpdate
@EntityListeners({AuditingEntityListener.class})
public class ServicePremi {

    @Id
    private Long semiServId;

    private BigDecimal semiPremiDebet;

    private BigDecimal semiPremiCredit;

    private String semiPaidType;

    private String semiStatus;

    @LastModifiedDate
    private LocalDateTime semiModifiedDate;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "semi_serv_id", referencedColumnName = "serv_id", insertable = false, updatable = false)
    Services services;

}
