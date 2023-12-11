package com.app.smartdrive.api.repositories.HR;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;

import jakarta.transaction.Transactional;
@Repository
public interface EmployeeAreaWorkgroupRepository extends JpaRepository<EmployeeAreaWorkgroup, EmployeeAreaWorkgroupId> {
    @Query(value = "SELECT TOP(1) * FROM HR.EMPLOYEE_ARE_WORKGROUP ORDER BY eawg_id DESC", nativeQuery = true)
    Optional<EmployeeAreaWorkgroup> findLastOptional();

    @Query(value = "SELECT * FROM HR.EMPLOYEE_ARE_WORKGROUP WHERE eawg_id=?1", nativeQuery=true)
    Optional<EmployeeAreaWorkgroup> findByEawgId(Long eawgId);

    Page<EmployeeAreaWorkgroup> findByEawgArwgCodeOrEmployees_EmpNameContainingOrAreaWorkGroup_Cities_CityNameContaining(String value, String valueEmpName, String valueCityName, Pageable pageable);

    Optional<EmployeeAreaWorkgroup> findByEawgIdAndEawgEntityid(Long eawgId, Long eawgEntityId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from hr.employee_are_workgroup where eawg_id=:eawg_id", nativeQuery = true)
    void deleteEawgById(Long eawg_id);
    
    EmployeeAreaWorkgroup findByEawgArwgCode(String eawgArwgCode);
    EmployeeAreaWorkgroup findByAreaWorkGroup(AreaWorkGroup areaWorkGroup);

    @Transactional
    @Query(value = "SELECT * FROM hr.employee_are_workgroup WHERE eawg_entityid =:eawgEntityid AND eawg_arwg_code =:arwgCode", nativeQuery=true)
    public EmployeeAreaWorkgroup findByAgenAndArwgCode(Long eawgEntityid, String arwgCode);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE HR.EMPLOYEE_ARE_WORKGROUP SET EAWG_ENTITYID =?1 WHERE EAWG_ENTITYID =?2", nativeQuery = true) 
    int setEawgEntityid(Long eawgEntityidnow, Long eawgEntityid);

}
