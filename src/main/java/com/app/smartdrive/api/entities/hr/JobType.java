package com.app.smartdrive.api.entities.hr;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="jobtype",schema="hr")
public class JobType {
    @Id
    @Column(name="job_code")
    private String jobCode;

    @Column(name="job_modified_date")
    private LocalDateTime jobModifiedDate;

    @OneToMany(mappedBy = "employees", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Employees> job_entityid = new ArrayList<>();

}
