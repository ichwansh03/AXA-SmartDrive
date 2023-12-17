package com.smartdrive.serviceorderservice.repositories;

import com.smartdrive.serviceorderservice.entities.ClaimAssetEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaevRepository extends JpaRepository<ClaimAssetEvidence, Long> {

}
