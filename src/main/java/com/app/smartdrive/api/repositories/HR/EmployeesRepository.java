package com.app.smartdrive.api.repositories.HR;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;




@Repository
public interface EmployeesRepository  extends JpaRepository<Employees, Long>{

    Page<Employees> findByEmpGraduate(EnumClassHR.emp_graduate empGraduate, Pageable pageable);

    Page<Employees> findByEmpNameContaining(String empName, Pageable pageable);

    @Query(value = "SELECT * FROM hr.employees e WHERE e.emp_name LIKE (CONCAT('%', ?1, '%')) OR e.emp_graduate = ?1", nativeQuery = true)
    Page<Employees> findByEmpNameOrEmpGraduate(String searchValue, Pageable pageable);




}
