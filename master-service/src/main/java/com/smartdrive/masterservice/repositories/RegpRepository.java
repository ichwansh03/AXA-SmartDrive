package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.RegionPlat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegpRepository extends JpaRepository<RegionPlat, String> {
}
