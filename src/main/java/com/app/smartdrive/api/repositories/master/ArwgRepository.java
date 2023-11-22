package com.app.smartdrive.api.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.Cities;

public interface ArwgRepository extends JpaRepository<AreaWorkGroup, String>{
    AreaWorkGroup findByCities(Cities cities);
}
