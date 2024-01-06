package com.app.smartdrive.api.repositories.HR;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;

import java.time.LocalDate;


@Repository
public interface EmployeeSalaryDetailRepository extends JpaRepository<EmployeeSalaryDetail,Long> {

    boolean existsByEmsaEmpEntityidAndEmsaCreateDate(Long emsaEmpEntityid, LocalDate emsaCreateDate);
    
}
