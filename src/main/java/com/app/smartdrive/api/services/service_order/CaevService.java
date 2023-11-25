package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CaevService {

    List<ClaimAssetEvidence> findAllByCaevSeroId(Long seroId);
    ClaimAssetEvidence findById(Long caevId);

    List<ClaimAssetEvidence> findAllCaev();
}
