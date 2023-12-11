package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.Provinsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvRepository extends JpaRepository<Provinsi, Long> {
    
}
