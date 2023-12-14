package com.app.smartdrive.api.services.service_order.claims;

import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequest;
import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequestDto;
import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequestSparePartDto;
import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClaimAssetService {

    void init();

    ClaimAssetEvidence add(MultipartFile multipartFile, ClaimAssetEvidence caev) throws Exception;
    List<ClaimAssetEvidence> create(ClaimAssetRequestDto claimAssetRequestDto) throws IOException;

    List<ClaimAssetSparepart> create(ClaimAssetRequestSparePartDto claimAssetRequestDto) throws IOException;


}
