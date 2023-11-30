package com.app.smartdrive.api.repositories.HR;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.partner.Partner;

import jakarta.transaction.Transactional;

@Repository
public interface EmployeesRepository  extends JpaRepository<Employees, Long>{
    
    @Query("SELECT e FROM Employees e WHERE e.empName = :employeeName")
    List<Employees> findAllByEmpName(@Param("employeeName") String empName);

    Employees findByEmpName(String empName);
    
    // @Transactional
    // @Modifying(clearAutomatically = true)
    // @Query(value = "delete from hr.employees where emp_entityid=:emp_entityid", nativeQuery = true)
    // void deleteEmployeesById(Long emp_entityid);


    Page<Employees> findByEmpGraduate(EnumClassHR.emp_graduate empGraduate, Pageable pageable);

    Page<Employees> findByEmpNameContaining(String empName, Pageable pageable);

    
    
// Page<Employees> findByEmpNameContaining(String empName, Pageable pageable);
}
