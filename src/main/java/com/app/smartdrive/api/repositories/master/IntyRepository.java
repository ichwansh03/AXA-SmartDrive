package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntyRepository extends JpaRepository<InsuranceType, String> {
}
