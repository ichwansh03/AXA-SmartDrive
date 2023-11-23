package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaspRepository extends JpaRepository<ClaimAssetSparepart, Long> {

    List<ClaimAssetSparepart> findAllByCaspSeroId(String seroId);
}
