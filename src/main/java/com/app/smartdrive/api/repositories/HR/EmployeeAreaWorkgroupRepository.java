package com.app.smartdrive.api.repositories.HR;

import java.util.Optional;

import com.app.smartdrive.api.entities.hr.Employees;
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

    @Query(value = "SELECT * FROM HR.EMPLOYEE_ARE_WORKGROUP WHERE eawg_id=?1", nativeQuery=true)
    Optional<EmployeeAreaWorkgroup> findByEawgId(Long eawgId);

//    Page<EmployeeAreaWorkgroup> findByEawgArwgCodeOrEmployees_EmpNameContainingOrAreaWorkGroup_Cities_CityNameContaining(String value, String valueEmpName, String valueCityName, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from hr.employee_are_workgroup where eawg_id=:eawg_id", nativeQuery = true)
    void deleteEawgById(Long eawg_id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE HR.EMPLOYEE_ARE_WORKGROUP SET EAWG_ENTITYID =?1 WHERE EAWG_ENTITYID =?2", nativeQuery = true) 
    int setEawgEntityid(Long eawgEntityidnow, Long eawgEntityid);

    Optional<EmployeeAreaWorkgroup> findByAreaWorkGroupAndEmployees(AreaWorkGroup areaWorkGroup, Employees employees);

    boolean existsByEmployeesAndAreaWorkGroup(Employees employees, AreaWorkGroup areaWorkGroup);

    @Query(value = "SELECT * FROM hr.employee_are_workgroup eawg " +
            "LEFT JOIN hr.employees emp ON eawg.eawg_entityid = emp.emp_entityid " +
            "LEFT JOIN mtr.area_workgroup awg ON eawg.eawg_arwg_code = awg.arwg_code " +
            "LEFT JOIN mtr.cities c ON awg.arwg_city_id = c.city_id " +
            "WHERE eawg.eawg_arwg_code = :arwgCode OR emp.emp_name LIKE %:empName% OR c.city_name LIKE %:cityName%",
            nativeQuery = true)
    Page<EmployeeAreaWorkgroup> findeawg(String arwgCode, String empName, String cityName, Pageable pageable);
}
