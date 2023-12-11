package com.smartdrive.masterservice.repositories;

import java.util.List;

import com.smartdrive.masterservice.entities.AreaWorkGroup;
import com.smartdrive.masterservice.entities.Cities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArwgRepository extends JpaRepository<AreaWorkGroup, String>{
    AreaWorkGroup findByArwgCode(String arwgCode);
    AreaWorkGroup findByCities(Cities cities);

    List<AreaWorkGroup> findByArwgCodeIgnoreCase(String arwgCode);
    
}
