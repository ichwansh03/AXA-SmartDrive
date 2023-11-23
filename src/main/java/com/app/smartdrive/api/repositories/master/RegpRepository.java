package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.RegionPlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RegpRepository extends JpaRepository<RegionPlat, String> {
}
