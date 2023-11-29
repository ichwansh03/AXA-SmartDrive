package com.app.smartdrive.api.repositories.HR;


import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;

import java.util.Optional;

@Repository
public interface EmployeeAreaWorkgroupRepository extends JpaRepository<EmployeeAreaWorkgroup, Long > {
    EmployeeAreaWorkgroup findByEawgArwgCode(String eawgArwgCode);
    EmployeeAreaWorkgroup findByAreaWorkGroup(AreaWorkGroup areaWorkGroup);

    @Query(value = "SELECT * FROM HR.EMPLOYEE_ARE_WORKGROUP WHERE eawg_id=?1", nativeQuery=true)
    Optional<EmployeeAreaWorkgroup> findByEawgId(Long eawgId);
}
