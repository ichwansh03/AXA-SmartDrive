package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntyRepository extends JpaRepository<InsuranceType, String> {
}
