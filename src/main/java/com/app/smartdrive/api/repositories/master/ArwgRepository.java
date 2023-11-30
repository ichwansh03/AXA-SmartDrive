package com.app.smartdrive.api.repositories.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.Cities;

public interface ArwgRepository extends JpaRepository<AreaWorkGroup, String>{
    AreaWorkGroup findByArwgCode(String arwgCode);
    AreaWorkGroup findByCities(Cities cities);

    List<AreaWorkGroup> findByArwgCodeIgnoreCase(String arwgCode);
    
}
