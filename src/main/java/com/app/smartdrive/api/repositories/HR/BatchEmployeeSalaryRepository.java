package com.app.smartdrive.api.repositories.HR;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;


@Repository
public interface BatchEmployeeSalaryRepository extends JpaRepository<BatchEmployeeSalary, Long> {
    
}
