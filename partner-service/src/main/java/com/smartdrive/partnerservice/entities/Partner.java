package com.smartdrive.partnerservice.entities;

import com.smartdrive.partnerservice.entities.enums.EnumClassHR;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "partners", schema = "partners")
@EntityListeners({AuditingEntityListener.class})
public class Partner {
    @Id
    @Column(name = "part_entityid")
    private Long partEntityid;
    @Column(name = "part_name")
    private String partName;
    @Column(name = "part_address")
    private String partAddress;
    @CreatedDate
    @Column(name = "part_join_date")
    private LocalDateTime partJoinDate;
    @Column(name = "part_accountno")
    private String partAccountNo;
    @Column(name = "part_npwp")
    private String partNpwp;
    @Enumerated(EnumType.STRING)
    @Column(name = "part_status")
    private EnumClassHR.status partStatus;

    {
        partStatus = EnumClassHR.status.ACTIVE;
    }

    @LastModifiedDate
    @Column(name = "part_modified_date")
    private LocalDateTime partModifiedDate;

    @OneToMany(mappedBy = "partner", fetch = FetchType.LAZY)
    private List<PartnerContact> partnerContacts;
}
