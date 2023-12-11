package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.Zones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonesRepository extends JpaRepository<Zones, Long> {
    
}
