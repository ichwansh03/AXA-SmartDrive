package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;

import java.util.List;

public interface CaspService {

    ClaimAssetSparepart findCaspById(Long caspId);

    List<ClaimAssetSparepart> findAllCasp();

    List<ClaimAssetSparepart> findAllByCaspSeroId(String seroId);
}
