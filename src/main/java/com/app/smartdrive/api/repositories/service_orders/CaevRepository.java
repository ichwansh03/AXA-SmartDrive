package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaevRepository extends JpaRepository<ClaimAssetEvidence, Long> {
}
