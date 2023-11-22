package com.app.smartdrive.api.repositories.HR;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.Employees;

@Repository
public interface EmployeesRepository  extends JpaRepository<Employees, Long>{
    
    @Query("SELECT e FROM Employees e WHERE e.empName = :employeeName")
    List<Employees> findAllByEmployeeName(@Param("employeeName") String employeeName);
}
