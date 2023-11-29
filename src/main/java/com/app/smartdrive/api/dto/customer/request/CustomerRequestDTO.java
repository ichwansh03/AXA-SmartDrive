package com.app.smartdrive.api.dto.customer.request;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.customer.CustomerClaim;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    private Long creqEntityId;
        
//    private LocalDateTime creqCreateDate;
//
//    private EnumCustomer.CreqStatus creqStatus;
//
//    private EnumCustomer.CreqType creqType;
//
//    private LocalDateTime creqModifiedDate;
//
    private Long creq_cust_entityid;
    
    private String arwg_code;

    private CiasDTO ciasDTO;
}
