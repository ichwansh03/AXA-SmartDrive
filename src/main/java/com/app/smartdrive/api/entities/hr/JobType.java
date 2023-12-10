package com.app.smartdrive.api.entities.hr;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.smartdrive.api.entities.users.UserAddress;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="job_type",schema="hr")
public class JobType {
    @Id
    @Column(name="job_code")
    private String jobCode;

    @Column(name="job_modified_date")
    private LocalDateTime jobModifiedDate;

    @Column(name = "job_desc")
    private String jobDesc;

    @Column(name = "job_rate_min")
    private Double jobRateMin;

    @Column(name = "job_rate_max")
    private Double jobRateMax;

    // @PrimaryKeyJoinColumn
    @OneToMany(mappedBy = "jobType", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Employees> employees ;

}
