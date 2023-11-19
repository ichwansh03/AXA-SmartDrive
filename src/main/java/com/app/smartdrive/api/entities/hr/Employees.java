package com.app.smartdrive.api.entities.hr;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="employees",schema="hr")
public class Employees {

    //tinggal masukin dan hubungin sama user entity id

    @Id
    @OneToOne
    @MapsId
    @Column(name="emp_entityid")
    private Long empEntityid;

    @Column(name="emp_name", nullable = false, length = 85)
    private String empName;
   
    @Column(name="emp_join_date")
    private LocalDateTime empJoinDate;

    @Column(name="emp_type", length = 15)
    @Enumerated(EnumType.STRING)
    private EnumClassHR.emp_type empType;

    @Column(name="emp_status", length = 15)
    @Enumerated(EnumType.STRING)
    private EnumClassHR.status empStatus;

    @Column(name="emp_graduate", length = 15)
    @Enumerated(EnumType.STRING)
    private EnumClassHR.emp_graduate empGraduate;

    @Column(name="emp_net_alary")
    private Double empNetSalary;

    @Column(name="emp_account_number", length = 35)
    private String empAccountNumber;

    @ManyToOne
    @Column(name="emp_job_code")
    private JobType jobTypeCode;

    @OneToMany(mappedBy="employees", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private List<BatchEmployeeSalary> batchEmployeeSalary = new ArrayList<>();

    @OneToMany(mappedBy="employees", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private List<EmployeeSalaryDetail> employeeSalaryDetails = new ArrayList<>();

    @OneToMany(mappedBy="employees", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private List<EmployeeAreaWorkgroup> employeeAreaWorkgroup = new ArrayList<>();
}
