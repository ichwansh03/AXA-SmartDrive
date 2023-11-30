package com.app.smartdrive.api.repositories.HR;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;
import com.app.smartdrive.api.entities.users.UserAddress;

import jakarta.transaction.Transactional;

@Repository
public interface EmployeeAreaWorkgroupRepository extends JpaRepository<EmployeeAreaWorkgroup, EmployeeAreaWorkgroupId> {
    @Query(value = "SELECT TOP(1) * FROM HR.EMPLOYEE_ARE_WORKGROUP ORDER BY eawg_id DESC", nativeQuery = true)
    Optional<EmployeeAreaWorkgroup> findLastOptional();

    @Query(value = "SELECT * FROM HR.EMPLOYEE_ARE_WORKGROUP WHERE eawg_id=?1", nativeQuery=true)
    Optional<EmployeeAreaWorkgroup> findByEawgId(Long eawgId);

    Page<EmployeeAreaWorkgroup> findByEawgArwgCodeOrEmployees_EmpNameContainingOrAreaWorkGroup_Cities_CityNameContaining(String value, String valueEmpName, String valueCityName, Pageable pageable);

    



    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from hr.employee_are_workgroup where eawg_id=:eawg_id", nativeQuery = true)
    void deleteEawgById(Long eawg_id);
    
}
