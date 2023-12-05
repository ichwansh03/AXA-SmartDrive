package com.app.smartdrive.api.entities.partner;

import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

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

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "part_entityid")
    private BusinessEntity businessEntity;

    @ManyToOne
    @JoinColumn(name = "part_city_id", nullable = false)
    private Cities city;

    @OneToMany(mappedBy = "partner", fetch = FetchType.LAZY)
    private List<PartnerContact> partnerContacts;

    @OneToMany(mappedBy = "caevPartners", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ClaimAssetEvidence> claimAssetEvidence;

    @OneToMany(mappedBy = "caspPartners", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ClaimAssetSparepart> claimAssetSparepart;

}
