package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import org.springframework.web.multipart.MultipartFile;

public interface ClaimAssetService {

    ClaimAssetEvidence addAssetEvidence(MultipartFile multipartFile, ClaimAssetEvidence caev) throws Exception;
}
