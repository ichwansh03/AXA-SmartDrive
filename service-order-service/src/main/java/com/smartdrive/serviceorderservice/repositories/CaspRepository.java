package com.smartdrive.serviceorderservice.repositories;

import com.smartdrive.serviceorderservice.entities.ClaimAssetSparepart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaspRepository extends JpaRepository<ClaimAssetSparepart, Long> {

    List<ClaimAssetSparepart> findAllByCaspSeroId(String seroId);
}
