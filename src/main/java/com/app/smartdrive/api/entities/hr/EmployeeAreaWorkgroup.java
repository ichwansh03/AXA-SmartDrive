package com.app.smartdrive.api.entities.hr;

import java.time.LocalDateTime;
import java.util.List;

import com.app.smartdrive.api.entities.customer.CustomerInscDocId;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@IdClass(EmployeeAreaWorkgroupId.class)
@Table(name="employee_are_workgroup",schema="hr")
public class EmployeeAreaWorkgroup {

    @Id
    @Column(name = "eawg_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, 
      generator = "eawgId-generator")
    @SequenceGenerator(
      name = "eawgId-generator",
      sequenceName = "employee_are_workgroup_seq",
      allocationSize = 1, schema = "hr"
    )
    private Long eawgId;

    @Id
    @Column(name = "eawg_entityid")
    private Long eawgEntityid;

    @Column(name="eawg_modified_date")
    private LocalDateTime eawgModifiedDate;


    @Column(name = "eawg_arwg_code",length=15)
    private String eawgArwgCode;


//    @MapsId("eawgArwgCode")
    @ManyToOne
    @JoinColumn(name = "eawg_arwg_code",insertable = false, updatable = false)
    @JsonBackReference
    private AreaWorkGroup areaWorkGroup;


    @ManyToOne
    @MapsId("eawgEntityid")
    @JoinColumn(name = "eawg_entityid")
    @JsonIgnore
    private Employees employees;


//     @JsonManagedReference
//     @OneToMany(mappedBy = "employeeAreaWorkgroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//     private List<CustomerRequest> customerRequests;




}
